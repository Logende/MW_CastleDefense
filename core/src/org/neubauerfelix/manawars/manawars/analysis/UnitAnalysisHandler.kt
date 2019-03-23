package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.DataUnitLoaded
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.animation.human.EntityAnimationProducerHuman
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class UnitAnalysisHandler : IUnitAnalysisHandler {

    private val config: Configuration = ConfigurationProvider.getProvider(YamlConfiguration::class.java).
            load("content/units/${MConstants.UNIT_ANALYSIS_FILE_NAME}", true)


    override fun analyseUnits(fileName: String) {
        val config = Configuration()
        for (data in MManaWars.m.getUnitHandler().listUnits()) {
            if (data is DataUnitLoaded) {
                val section = config.getSection(data.name)
                data.analyseUnit()
                val analysis = data.analysis
                section.set("actionValue", analysis.actionValue)
                section.set("survivalFactor", analysis.survivalFactor)
                section.set("defensiveStrengthPerSecond", analysis.defensiveStrengthPerSecond)
                section.set("offensiveStrengthPerSecond", analysis.offensiveStrengthPerSecond)
                section.set("cost", analysis.cost)
            }
        }
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(config, fileName, false)
    }

    override fun loadUnitAnalysis(data: IDataUnit): IUnitAnalysis {
        // no analysis existing? Return dummy
        if (! config.contains(data.name)) {
            return UnitAnalysisDummy()
        }

        val section = config.getSection(data.name)
        return object : AUnitAnalysis() {
            override val actionValue: Float = section.getFloat("actionValue")
            override val survivalFactor: Float = section.getFloat("survivalFactor")
            override val defensiveStrengthPerSecond: Float = section.getFloat("defensiveStrengthPerSecond")
            override val offensiveStrengthPerSecond: Float = section.getFloat("offensiveStrengthPerSecond")
            override val cost: Int = section.getInt("cost")
            override val armor: Map<MWArmorHolder, MWArmorType> = data.armor // little shortcut
        }

    }


    override fun analyse(data: IDataUnit): IUnitAnalysis {
        val actionAnalysis = data.action.getActionProperties(data.animation.animationType)
        val animation = data.animation


        val survivalFactorHealth = 1.0f + (data.health - MConstants.UNIT_AVG_HEALTH) / 400.0f

        // Calculates the average damage factor for every armor and multiplies the factors
        // Avg damage factors of armor are calculated via share and effectivity of every skillclass
        val survivalFactorArmor = 1f / animation.animationType.armorHolders.map { armorHolder ->
            val armor = if (data.armor.containsKey(armorHolder)) data.armor[armorHolder]!! else MWArmorType.NONE
            val avgDamageFactor = MWSkillClass.values().map { skillClass ->
                MAnalysisConstants.SKILL_CLASS_SHARES[skillClass]!! * armor.getSkillEffectivity(skillClass).damageFactor
            }.sum()
            avgDamageFactor * MAnalysisConstants.ARMOR_HOLDER_HIT_SHARES[animation.animationType]!!.get(armorHolder)!!
        }.sum()

        val survivalFactorRange = 0.755f + actionAnalysis.rangeMaxAvg / 3401f


        val survivalFactor = survivalFactorHealth * survivalFactorArmor * survivalFactorRange


        // 1 action value = 1 damage per second
        val actionValue: Float = actionAnalysis.strategicValue * actionAnalysis.successProbability / data.actionCooldown
        val defensiveStrengthPerSecond: Float = actionAnalysis.defensiveStrength / data.actionCooldown
        val offensiveStrengthPerSecond: Float = actionAnalysis.offensiveStrength / data.actionCooldown


        val cost = survivalFactor * (actionValue * 2.0
                + offensiveStrengthPerSecond * 0.35f
                + defensiveStrengthPerSecond * 0.35f
                + data.health / MAnalysisConstants.UNIT_AVG_SURVIVAL_DURATION)



        return object : AUnitAnalysis() {
            override val actionValue: Float = actionValue
            override val survivalFactor: Float = survivalFactor
            override val defensiveStrengthPerSecond: Float = defensiveStrengthPerSecond
            override val offensiveStrengthPerSecond: Float = offensiveStrengthPerSecond
            override val armor: Map<MWArmorHolder, MWArmorType> = data.armor
            override val cost: Int = cost.toInt()

        }
    }

    override fun getAttackerStrategicFactor(attacker: IDataUnit, target: IDataUnit): Float {
        var actionFactor = 1f

        val actionPropertiesAttacker = attacker.action.getActionProperties(attacker.animation.animationType)
        val actionPropertiesTarget = target.action.getActionProperties(target.animation.animationType)

        // If has skill
        if (actionPropertiesAttacker is ISkillAnalysis) {
            // Check if target has armor
            actionFactor *= actionPropertiesAttacker.collisionsPercentages.get(target.animation.animationType)!!.map { pair ->
                val armorHolder = pair.key
                val percentage = pair.value
                val armor = if (target.armor.containsKey(armorHolder)) target.armor[armorHolder]!! else MWArmorType.NONE
                val armorFactor = armor.getSkillEffectivity(actionPropertiesAttacker.skillClass).damageFactor
                armorFactor * percentage
            }.sum().toFloat()
        }

        val rangeFactor = actionPropertiesAttacker.rangeMaxAvg / actionPropertiesTarget.rangeMaxAvg

        return actionFactor * rangeFactor
    }
}