package org.neubauerfelix.manawars.manawars.enums

import java.util.HashMap

enum class MWArmorType { //Keep those as simple as possible!

    NONE {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            return hashMapOf()
        }
    },

    ANTI_LIGHT {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            val map = HashMap<MWSkillClass, MWSkillEffectivity>()
            map[MWSkillClass.LIGHT] = MWSkillEffectivity.IMMUNE
            map[MWSkillClass.MAGIC] = MWSkillEffectivity.SUPER_EFFECTIVE
            return map
        }
    },
    ANTI_MAGIC_LIGHT {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            val map = HashMap<MWSkillClass, MWSkillEffectivity>()
            map[MWSkillClass.MAGIC] = MWSkillEffectivity.IMMUNE
            map[MWSkillClass.LIGHT] = MWSkillEffectivity.IMMUNE
            return map
        }
    };

    val skillEffectivities = createSkillEffectivity()

    protected abstract fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity>

    fun getSkillEffectivity(skillclass: MWSkillClass): MWSkillEffectivity {
        return if (skillEffectivities.containsKey(skillclass)) {
            skillEffectivities[skillclass]!!
        } else MWSkillEffectivity.NORMAL
    }
}