package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.DataUnitLoaded
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
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
            override val armor: MWArmorType = data.armor // little shortcut
        }

    }


    override fun analyse(data: IDataUnit, league: IDataLeague): IUnitAnalysis {
        val actionAnalysis = data.action.getActionProperties(data.animation.animationType)
        val animation = data.animation


        // Calculates the average damage factor for every armor and multiplies the factors
        // Avg damage factors of armor are calculated via share and effectivity of every skillclass
        val survivalFactorArmor = 1f / MWSkillClass.values().map { skillClass ->
                MAnalysisConstants.SKILL_CLASS_SHARES[skillClass]!! * data.armor.getSkillEffectivity(skillClass).damageFactor
            }.sum()

        val survivalFactorRange = 0.755f + actionAnalysis.rangeMaxAvg / 3401f
        val defensiveStrengthPerSecond: Float = Math.max(0.001f, actionAnalysis.defensiveStrength / data.actionCooldown)
        val survivalFactorStrength = 0.8f + defensiveStrengthPerSecond * 0.03f
        val survivalFactor = survivalFactorArmor * survivalFactorRange * survivalFactorStrength

        val offensiveStrengthPerSecond: Float = Math.max(0.001f, actionAnalysis.offensiveStrength / data.actionCooldown)


        // 1 action value = 1 damage per second
        val actionValue: Float = actionAnalysis.strategicValue / data.actionCooldown

        val cost = survivalFactor * data.health +
                actionValue * 4

        println("Generated cost of unit ${data.name}: $cost with survF $survivalFactor, health ${data.health}")

        return object : AUnitAnalysis() {
            override val actionValue: Float = actionValue
            override val survivalFactor: Float = survivalFactor
            override val defensiveStrengthPerSecond: Float = defensiveStrengthPerSecond
            override val offensiveStrengthPerSecond: Float = offensiveStrengthPerSecond
            override val armor: MWArmorType = data.armor
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
            actionFactor *= target.armor.getSkillEffectivity(actionPropertiesAttacker.skillClass).damageFactor
        }

        val rangeFactor = actionPropertiesAttacker.rangeMaxAvg / actionPropertiesTarget.rangeMaxAvg

        return actionFactor * rangeFactor
    }
}