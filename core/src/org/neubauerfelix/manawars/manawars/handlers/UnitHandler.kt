package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.DataUnitLoaded
import org.neubauerfelix.manawars.manawars.data.actions.IDataUnit
import org.neubauerfelix.manawars.manawars.data.actions.IUnitAnalysis
import org.neubauerfelix.manawars.manawars.data.actions.IUnitAnalysisPart
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.data.units.IUnitAnalysis
import org.neubauerfelix.manawars.manawars.entities.*
import org.neubauerfelix.manawars.manawars.entities.MUnit
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration
import java.lang.RuntimeException


class UnitHandler : IUnitAnalysisHandler {


    init {
        this.loadUnitAnalyses("content/units/unitanalysis.yml")
    }


    override fun analyseUnits(fileName: String) {
        val config = Configuration()
        for (data in MManaWars.m.getActionHandler().listActions()) {
            if (data is DataUnitLoaded) {
                val section = config.getSection(data.name)
                data.analyseUnit()
                val map = data.analysis
                for ((type, analysis) in map) {
                    section.set("${type.name}.collisionPercentageHumanBody", analysis.collisionsPercentageHumanBody)
                    section.set("${type.name}.collisionPercentageHumanHead", analysis.collisionsPercentageHumanHead)
                    section.set("${type.name}.collisionPercentageMount", analysis.collisionsPercentageMount)
                    section.set("${type.name}.height", analysis.height)
                    section.set("${type.name}.width", analysis.width)
                    section.set("${type.name}.successProbability", analysis.successProbability)
                    section.set("${type.name}.lifeTime", analysis.lifeTime)
                    section.set("${type.name}.strategicValue", analysis.strategicValue)
                    section.set("${type.name}.offensiveStrength", analysis.offensiveStrength)
                    section.set("${type.name}.defensiveStrength", analysis.defensiveStrength)
                    for ( (type, range) in analysis.rangeMax) {
                        section.set("${type.name}.rangeMax.${type.name}", range)
                    }
                    for ( (type, range) in analysis.rangeMin) {
                        section.set("${type.name}.rangeMin.${type.name}", range)
                    }
                }
            }
        }
        ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(config, fileName, false)
    }

    override fun loadUnitAnalyses(fileName: String) {
        val config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load(fileName, true)
        for (data in MManaWars.m.getActionHandler().listActions()) {
            if (data is DataUnitLoaded) {
                val section = config.getSection(data.name)
                val map = data.analysis
                for ((type, analysisDummy) in map) {
                    val collisionPercentageHumanBody = section.getFloat("collisionPercentageHumanBody")
                    val collisionsPercentageHumanHead = section.getFloat("collisionsPercentageHumanHead")
                    val collisionsPercentageMount = section.getFloat("collisionsPercentageMount")
                    val height = section.getInt("height")
                    val width = section.getInt("width")
                    val successProbability = section.getFloat("successProbability")
                    val lifeTime = section.getFloat("lifeTime")
                    val strategicValue = section.getFloat("strategicValue")
                    val offensivePoints = section.getFloat("offensiveStrength")
                    val defensivePoints = section.getFloat("defensiveStrength")
                    val rangeMax: MutableMap<MWEntityAnimationType, Int> = HashMap()
                    val rangeMin: MutableMap<MWEntityAnimationType, Int> = HashMap()
                    for ( (type, range) in analysisDummy.rangeMax) {
                        rangeMax[type] = section.getInt("${type.name}.rangeMax.${type.name}")
                    }
                    for ( (type, range) in analysisDummy.rangeMin) {
                        rangeMin[type] = section.getInt("${type.name}.rangeMin.${type.name}")
                    }

                    data.analysis[type] = object : IUnitAnalysis {
                        override val lifeTime: Float = lifeTime
                        override val width: Int = width
                        override val height: Int = height
                        override val strategicValue: Float = strategicValue
                        override val successProbability: Float = successProbability
                        override val offensiveStrength: Float = offensivePoints
                        override val defensiveStrength: Float = defensivePoints
                        override val collisionsPercentageHumanHead: Float = collisionsPercentageHumanHead
                        override val collisionsPercentageHumanBody: Float = collisionPercentageHumanBody
                        override val collisionsPercentageMount: Float = collisionsPercentageMount
                        override val rangeMax: Map<MWEntityAnimationType, Int> = rangeMax
                        override val rangeMin: Map<MWEntityAnimationType, Int> = rangeMin
                        override val unitClass: MWUnitClass = data.unitClass
                    }
                }
            }
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

        return object : IUnitAnalysis {
            override val actionValue: Float = actionAnalysis.strategicValue * actionAnalysis.successProbability / data.actionCooldown
            override val survivalFactor: Float = survivalFactor
            override val defensiveStrengthPerSecond: Float = actionAnalysis.defensiveStrength / data.actionCooldown
            override val offensiveStrengthPerSecond: Float = actionAnalysis.offensiveStrength / data.actionCooldown
            override val armor: Map<MWArmorHolder, MWArmorType> = data.armor
        }
    }


}