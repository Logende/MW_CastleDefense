package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.manawars.enums.MWArmorHolder
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass

class SkillAnalysisDummy : ISkillAnalysis {

    override val lifeTime: Float = 10f
    override val width: Int = 300
    override val height: Int = 300
    override val strategicValue: Float = 10f
    override val successProbability: Float = 1.0f
    override val offensiveStrength: Float = 10f
    override val defensiveStrength: Float = 10f
    override val collisionsPercentages: Map<MWArmorHolder, Double> = hashMapOf(Pair(MWArmorHolder.HUMAN_HEAD, 0.2),
            Pair(MWArmorHolder.HUMAN_BODY, 0.75), Pair(MWArmorHolder.MOUNT, 0.05))
    override val rangeMax: Map<MWEntityAnimationType, Int>
    override val rangeMin: Map<MWEntityAnimationType, Int>
    override val skillClass: MWSkillClass = MWSkillClass.NORMAL
    override val rangeMaxAvg: Float = 1000f

    init {
        rangeMax = HashMap()
        MWEntityAnimationType.values().forEach { type ->
            rangeMax[type] = 1000
        }
        rangeMin = HashMap()
        MWEntityAnimationType.values().forEach { type ->
            rangeMin[type] = 0
        }
    }

}