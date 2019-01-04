package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MManaWars


enum class MWSkillClass private constructor(val durabilityUpgrade: MWUpgrade, val damageUpgrade: MWUpgrade, val type: Int //0: Normal & blocked by shield. 1: Can go through normal shields and blocked by heavy skills. 2: Can go through all shields.
) {
    LIGHT(MWUpgrade.SKILLS_DURABILITY_LIGHT, MWUpgrade.SKILLS_LIGHT, 0),
    HEAVY(MWUpgrade.SKILLS_DURABILITY_HEAVY, MWUpgrade.SKILLS_HEAVY, 1),
    MAGIC(MWUpgrade.SKILLS_DURABILITY_MAGIC, MWUpgrade.SKILLS_MAGIC, 0),
    SHIELD(null!!, null!!, 2);

    val displayName: String
        get() = MManaWars.m.getLanguageHandler().getMessage("information_skillclass_" + name.toLowerCase())

    companion object {

        val TYPE_NORMAL = 0
        val TYPE_STRONG = 1
        val TYPE_UNDESTROYABLE = 2
    }
}