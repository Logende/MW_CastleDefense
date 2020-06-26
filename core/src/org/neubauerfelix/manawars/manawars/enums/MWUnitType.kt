package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MManaWars

enum class MWUnitType(val defaultRange: Float, val main: Boolean, val index: Int) {

    // Note that the main units here need to be in the same order as the indices
    BOSS(1000f, true, 0),
    MELEE(700f, true, 1),
    KNIGHT(800f, true, 2),
    ARCHER(1500f, true, 3),
    MAGE(900f, true, 4),
    BUILDER(0f, false, -1),
    BUILDING(1100f, false, -1);


    val displayName: String
        get() = MManaWars.m.getLanguageHandler().getMessage("unittype_${name.toLowerCase()}")

    companion object {
        fun getEffectivity(attackerType: MWUnitType, victimType: MWUnitType) : MWSkillEffectivity {
            return when(attackerType) {
                ARCHER -> if (victimType == KNIGHT) MWSkillEffectivity.SUPER_WEAK else MWSkillEffectivity.NORMAL
                else -> MWSkillEffectivity.NORMAL
            }
        }
    }

}