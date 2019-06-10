package org.neubauerfelix.manawars.manawars.enums

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit


enum class MWUpgrade constructor(val actionType: Int //0 = Addition, 1 = Subtraction, 2 = Factor increase, 3 = Factor decrease, 4 = Unique action
                                         , private val name_path: String, val previewTexturePath: String, val isPercentage: Boolean) {
    HEALTH(2, "health", "upgrade.health", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            unit.health = this.apply(unit.health, upgradeValue)
        }
    },
    COOLDOWN(2, "cooldown", "upgrade.cooldown", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            unit.actionCooldown = this.apply(unit.actionCooldown, upgradeValue)
        }
    },
    WALKSPEED(2, "walkspeed", "upgrade.walkspeed", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            unit.walkSpeedMax = this.apply(unit.walkSpeedMax, upgradeValue)
            unit.walkAcceleration = this.apply(unit.walkAcceleration, upgradeValue)
        }
    },
    DRAIN(0, "drain", "upgrade.drain", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            unit.drainMultiplier = this.apply(unit.drainMultiplier, upgradeValue)
        }
    },

    // TODO
    /*AURA(0, "aura", "upgrade.aura", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            unit.health = this.apply(unit.health, upgradeValue)
        }
    },*/

    LOOT_LUCK(3, "loot_luck", "upgrade.luck", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            // not a unit skill but instead affects castle
        }
    },

    SKILLS_NORMAL(2, "skills_heavy", "upgrade.skills.heavy", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            val value = if (unit.skillMultipliers.contains(MWSkillClass.NORMAL))
                unit.skillMultipliers[MWSkillClass.NORMAL]!! else 1f
            unit.skillMultipliers[MWSkillClass.NORMAL] = this.apply(value, upgradeValue)
        }
    },
    SKILLS_LIGHT(2, "skills_light", "upgrade.skills.light", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            val value = if (unit.skillMultipliers.contains(MWSkillClass.LIGHT))
                unit.skillMultipliers[MWSkillClass.LIGHT]!! else 1f
            unit.skillMultipliers[MWSkillClass.LIGHT] = this.apply(value, upgradeValue)
        }
    },
    SKILLS_MAGIC(2, "skills_magic", "upgrade.skills.magic", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            val value = if (unit.skillMultipliers.contains(MWSkillClass.MAGIC))
                unit.skillMultipliers[MWSkillClass.MAGIC]!! else 1f
            unit.skillMultipliers[MWSkillClass.MAGIC] = this.apply(value, upgradeValue)
        }
    },
    SKILLS_SHIELD(2, "skills_shield", "upgrade.skills.shield", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            val value = if (unit.skillMultipliers.contains(MWSkillClass.SHIELD))
                unit.skillMultipliers[MWSkillClass.SHIELD]!! else 1f
            unit.skillMultipliers[MWSkillClass.SHIELD] = this.apply(value, upgradeValue)
        }
    },

    SKILLS_DURABILITY_NORMAL(2, "skills_durability_heavy", "upgrade.skills.durability.heavy", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            val value = if (unit.skillDurabilityMultipliers.contains(MWSkillClass.NORMAL))
                unit.skillDurabilityMultipliers[MWSkillClass.NORMAL]!! else 1f
            unit.skillDurabilityMultipliers[MWSkillClass.NORMAL] = this.apply(value, upgradeValue)
        }
    },
    SKILLS_DURABILITY_LIGHT(2, "skills_durability_light", "upgrade.skills.durability.light", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            val value = if (unit.skillDurabilityMultipliers.contains(MWSkillClass.LIGHT))
                unit.skillDurabilityMultipliers[MWSkillClass.LIGHT]!! else 1f
            unit.skillDurabilityMultipliers[MWSkillClass.LIGHT] = this.apply(value, upgradeValue)
        }
    },
    SKILLS_DURABILITY_MAGIC(2, "skills_durability_magic", "upgrade.skills.durability.magic", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            val value = if (unit.skillDurabilityMultipliers.contains(MWSkillClass.MAGIC))
                unit.skillDurabilityMultipliers[MWSkillClass.MAGIC]!! else 1f
            unit.skillDurabilityMultipliers[MWSkillClass.MAGIC] = this.apply(value, upgradeValue)
        }
    },
    SKILLS_DURABILITY_SHIELD(2, "skills_durability_shield", "upgrade.skills.durability.shield", true) {
        override fun apply(unit: IDataUnit, upgradeValue: Float) {
            val value = if (unit.skillDurabilityMultipliers.contains(MWSkillClass.SHIELD))
                unit.skillDurabilityMultipliers[MWSkillClass.SHIELD]!! else 1f
            unit.skillDurabilityMultipliers[MWSkillClass.SHIELD] = this.apply(value, upgradeValue)
        }
    };

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

    abstract fun apply(unit: IDataUnit, upgradeValue: Float)
}
