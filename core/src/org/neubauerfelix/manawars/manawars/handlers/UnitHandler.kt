package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.analysis.AUnitAnalysis
import org.neubauerfelix.manawars.manawars.analysis.ISkillAnalysis
import org.neubauerfelix.manawars.manawars.data.units.DataUnitLoaded
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.analysis.IUnitAnalysis
import org.neubauerfelix.manawars.manawars.analysis.UnitAnalysisDummy
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class UnitHandler : IUnitHandler {

    private val units = java.util.HashMap<String, IDataUnit>()

    private val config: Configuration = ConfigurationProvider.getProvider(YamlConfiguration::class.java).
            load("content/units/${MConstants.UNIT_ANALYSIS_FILE_NAME}", true)


    override fun putUnit(unit: IDataUnit) {
        units[unit.name] = unit
        System.out.println("put unit")
    }

    override fun getUnit(name: String): IDataUnit? {
        return units[name]
    }

    override fun listUnits(): Iterable<IDataUnit> {
        return units.values
    }



    override fun analyseUnits(fileName: String) {
        System.out.println("analyse units")
        val config = Configuration()
        for (data in listUnits()) {
            System.out.println("foundunit ${data.name}")
            if (data is DataUnitLoaded) {
                val section = config.getSection(data.name)
                data.analyseUnit()
                val analysis = data.analysis
                section.set("actionValue", analysis.actionValue)
                section.set("survivalFactor", analysis.survivalFactor)
                section.set("defensiveStrengthPerSecond", analysis.defensiveStrengthPerSecond)
                section.set("offensiveStrengthPerSecond", analysis.offensiveStrengthPerSecond)
            }
        }
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(config, fileName, false)
    }

    override fun loadUnitAnalysis(data: IDataUnit): IUnitAnalysis {
        // no analysis existing? Return dummy
        if (! config.contains(data.name)) {
            print("No analysis of unit ${data.name} found. Using dummy.")
            return UnitAnalysisDummy()
        }

        val section = config.getSection(data.name)
        return object : AUnitAnalysis() {
            override val actionValue: Float = section.getFloat("actionValue")
            override val survivalFactor: Float = section.getFloat("survivalFactor")
            override val defensiveStrengthPerSecond: Float = section.getFloat("defensiveStrengthPerSecond")
            override val offensiveStrengthPerSecond: Float = section.getFloat("offensiveStrengthPerSecond")
            override val armor: Map<MWArmorHolder, MWArmorType> = data.armor // little shortcut
        }

    }


    override fun analyse(data: IDataUnit): IUnitAnalysis {
        val actionAnalysis = data.action.getActionProperties(data.animation.animationType)


        val survivalFactorHealth = 1.0f + (data.health - MConstants.UNIT_AVG_HEALTH) / 400.0f

        // Calculates the average damage factor for every armor and multiplies the factors
        // Avg damage factors of armor are calculated via share and effectivity of every skillclass
        val survivalFactorArmor = data.animation.animationType.armorHolders.map { armorHolder ->
            val armor = if (data.armor.containsKey(armorHolder)) data.armor[armorHolder]!! else MWArmorType.NONE
            val avgDamageFactor = MWSkillClass.values().map { skillClass ->
                skillClass.share * armor.getSkillEffectivity(skillClass).damageFactor
            }.sum()
            avgDamageFactor
        }.reduce { acc, unit ->  acc * unit}

        val survivalFactorRange = 0.755f + actionAnalysis.rangeMaxAvg / 3401f

        val survivalFactor = survivalFactorHealth * survivalFactorArmor * survivalFactorRange



        return object : AUnitAnalysis() {
            override val actionValue: Float = actionAnalysis.strategicValue * actionAnalysis.successProbability / data.actionCooldown
            override val survivalFactor: Float = survivalFactor
            override val defensiveStrengthPerSecond: Float = actionAnalysis.defensiveStrength / data.actionCooldown
            override val offensiveStrengthPerSecond: Float = actionAnalysis.offensiveStrength / data.actionCooldown
            override val armor: Map<MWArmorHolder, MWArmorType> = data.armor

        }
    }


}