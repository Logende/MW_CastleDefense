package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.Color
import java.util.HashMap

enum class MWArmorType(val share: Float, val color: Color) { //Keep those as simple as possible!

    NONE(0.7f, Color.WHITE) {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            return hashMapOf()
        }
    },

    ANTI_LIGHT(0.2f, Color.BROWN) {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            val map = HashMap<MWSkillClass, MWSkillEffectivity>()
            map[MWSkillClass.LIGHT] = MWSkillEffectivity.IMMUNE
            map[MWSkillClass.MAGIC] = MWSkillEffectivity.SUPER_EFFECTIVE
            return map
        }
    },
    ANTI_MAGIC(0.1f, Color.PURPLE) {
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