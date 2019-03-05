package org.neubauerfelix.manawars.manawars.analysis

abstract class AUnitAnalysis : IUnitAnalysis {


    override fun getDamageFactor(skillAnalysis: ISkillAnalysis): Float {
        return skillAnalysis.collisionsPercentages.map { (armorHolder, percentage) ->
            val armor = armor[armorHolder]!!
            armor.getSkillEffectivity(skillAnalysis.skillClass).damageFactor * percentage
        }.sum().toFloat()
    }

}