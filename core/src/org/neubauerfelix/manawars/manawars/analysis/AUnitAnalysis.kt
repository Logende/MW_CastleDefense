package org.neubauerfelix.manawars.manawars.analysis

abstract class AUnitAnalysis : IUnitAnalysis {


    override fun getDamageFactor(skillAnalysis: ISkillAnalysis): Float {
        return armor.getSkillEffectivity(skillAnalysis.skillClass).damageFactor
    }

}