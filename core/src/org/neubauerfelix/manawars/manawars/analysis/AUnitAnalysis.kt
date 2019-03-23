package org.neubauerfelix.manawars.manawars.analysis

abstract class AUnitAnalysis : IUnitAnalysis {


    override fun getDamageFactor(skillAnalysis: ISkillAnalysis): Float {
        // Calculate average damage factor for all target animation types
        return skillAnalysis.collisionsPercentages.map { (targetAnimationType, percentages) ->

            // Calculate damage factor against given target animation type
            val factor = percentages.map { (armorHolder, percentage) ->
                val armor = armor[armorHolder]!!
                armor.getSkillEffectivity(skillAnalysis.skillClass).damageFactor * percentage
            }.sum().toFloat()

            factor * MAnalysisConstants.ANIMATION_TYPE_SHARES[targetAnimationType]!!
        }.sum()
    }

}