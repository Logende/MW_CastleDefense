package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MManaWars


enum class MWUpgrade constructor(val actionType: Int //0 = Addition, 1 = Subtraction, 2 = Factor increase, 3 = Factor decrease, 4 = Unique action
                                         , private val name_path: String, val previewTexturePath: String, val isPercentage: Boolean) {
    HEALTH(2, "health", "upgrade.health", true),
    MANA_REGENERATION(2, "mana", "upgrade.mana", true),
    WALKSPEED(2, "walkspeed", "upgrade.walkspeed", true),
    DRAIN(0, "drain", "upgrade.drain", true),
    RANGE(0, "steroids", "upgrade.steroids", true),
    AURA(0, "aura", "upgrade.aura", true),

    LOOT_LUCK(3, "loot_luck", "upgrade.luck", true),

    SKILLS_HEAVY(2, "skills_heavy", "upgrade.skills.heavy", true),
    SKILLS_LIGHT(2, "skills_light", "upgrade.skills.light", true),
    SKILLS_MAGIC(2, "skills_magic", "upgrade.skills.magic", true),

    SKILLS_DURABILITY_HEAVY(2, "skills_durability_heavy", "upgrade.skills.durability.heavy", true),
    SKILLS_DURABILITY_LIGHT(2, "skills_durability_light", "upgrade.skills.durability.light", true),
    SKILLS_DURABILITY_MAGIC(2, "skills_durability_magic", "upgrade.skills.durability.magic", true);

    val isUnique: Boolean
        get() = actionType == 4
    val displayName: String
        get() = MManaWars.m.getLanguageHandler().getMessage("upgrade_" + name_path + "_name")

    fun getDisplayDescription(value: Float): String {
        val s = if (isUnique) {
            "!"
        } else if (isPercentage) {
            (value * 100).toInt().toString() + "%"
        } else {
            value.toInt().toString()
        }
        return MManaWars.m.getLanguageHandler().getMessage("upgrade_" + name_path + "_description").replace("%value%", s)
    }

    fun apply(start_value: Float, upgrade_value_complete: Float): Float {
        when (actionType) {
            0 -> return start_value + upgrade_value_complete
            1 -> return start_value - upgrade_value_complete
            2 -> return start_value * (1 + upgrade_value_complete)
            3 -> return start_value * (1 - upgrade_value_complete)
            else -> {
            }
        }
        throw RuntimeException("Invalid uprade actiontype.")
    }
}
