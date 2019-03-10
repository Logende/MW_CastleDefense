package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.manawars.MManaWars

enum class MWSkillClass(val durabilityUpgrade: MWUpgrade, val damageUpgrade: MWUpgrade, val type: Int, val share: Float,
                        val color: Color) {
    LIGHT(MWUpgrade.SKILLS_DURABILITY_LIGHT, MWUpgrade.SKILLS_LIGHT, 0, 0.2f, Color.BROWN),
    NORMAL(MWUpgrade.SKILLS_DURABILITY_NORMAL, MWUpgrade.SKILLS_NORMAL, 1, 0.5f, Color.WHITE),
    MAGIC(MWUpgrade.SKILLS_DURABILITY_MAGIC, MWUpgrade.SKILLS_MAGIC, 0, 0.2f, Color.PURPLE),
    SHIELD(MWUpgrade.SKILLS_DURABILITY_SHIELD, MWUpgrade.SKILLS_SHIELD, 2, 0.1f, Color.GREEN);

    val displayName: String
        get() = MManaWars.m.getLanguageHandler().getMessage("information_skillclass_" + name.toLowerCase())

    companion object {
        val TYPE_WEAK = 0
        val TYPE_NORMAL = 1
        val TYPE_UNDESTROYABLE = 2
    }
}