package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MManaWars

enum class MWUnitType(val defaultRange: Float, val main: Boolean, val index: Int) {

    // Note that the main units here need to be in the same order as the indices
    BOSS(1000f, true, 0),
    TANK(800f, true, 1),
    MELEE(700f, true, 2),
    RANGER(1900f, true, 3),
    MAGE(1900f, true, 4),
    BUILDER(0f, false, -1),
    BUILDING(1100f, false, -1);


    val displayName: String
        get() = MManaWars.m.getLanguageHandler().getMessage("unittype_${name.toLowerCase()}")

    companion object {
        fun getEffectivity(attackerType: MWUnitType, victimType: MWUnitType) : MWSkillEffectivity {
            return when(attackerType) {
                TANK -> if (victimType == MELEE) MWSkillEffectivity.EFFECTIVE else MWSkillEffectivity.NORMAL
                MELEE -> if (victimType == RANGER) MWSkillEffectivity.EFFECTIVE else MWSkillEffectivity.NORMAL
                RANGER -> if (victimType == MAGE) MWSkillEffectivity.EFFECTIVE else MWSkillEffectivity.NORMAL
                MAGE -> if (victimType == TANK) MWSkillEffectivity.EFFECTIVE else MWSkillEffectivity.NORMAL
                else -> MWSkillEffectivity.NORMAL
            }
        }
    }

}