package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.manawars.MManaWars

enum class MWSkillClass(val durabilityUpgrade: MWUpgrade, val damageUpgrade: MWUpgrade, val type: Int,
                        val color: Color) {
    LIGHT(MWUpgrade.SKILLS_DURABILITY_LIGHT, MWUpgrade.SKILLS_LIGHT, 0, Color.FOREST),
    NORMAL(MWUpgrade.SKILLS_DURABILITY_NORMAL, MWUpgrade.SKILLS_NORMAL, 1, Color.WHITE),
    MAGIC(MWUpgrade.SKILLS_DURABILITY_MAGIC, MWUpgrade.SKILLS_MAGIC, 0, Color.PURPLE),
    SHIELD(MWUpgrade.SKILLS_DURABILITY_SHIELD, MWUpgrade.SKILLS_SHIELD, 2, Color.GREEN);

    val displayName: String
        get() = MManaWars.m.getLanguageHandler().getMessage("information_skillclass_" + name.toLowerCase())

}