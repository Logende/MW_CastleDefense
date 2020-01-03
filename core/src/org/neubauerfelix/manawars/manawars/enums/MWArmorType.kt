package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.Color
import java.util.HashMap

enum class MWArmorType(val color: Color) { //Keep those as simple as possible!

    NONE(MWSkillClass.NORMAL.color) {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            return hashMapOf()
        }
    },

    ANTI_LIGHT(MWSkillClass.LIGHT.color) {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            val map = HashMap<MWSkillClass, MWSkillEffectivity>()
            map[MWSkillClass.LIGHT] = MWSkillEffectivity.IMMUNE
            return map
        }
    },

    SHIELD(Color.BLACK) {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            val map = HashMap<MWSkillClass, MWSkillEffectivity>()
            map[MWSkillClass.LIGHT] = MWSkillEffectivity.IMMUNE
            map[MWSkillClass.MAGIC] = MWSkillEffectivity.IMMUNE
            map[MWSkillClass.NORMAL] = MWSkillEffectivity.WEAK
            return map
        }
    },

    ANTI_MAGIC(MWSkillClass.MAGIC.color) {
        override fun createSkillEffectivity(): HashMap<MWSkillClass, MWSkillEffectivity> {
            val map = HashMap<MWSkillClass, MWSkillEffectivity>()
            map[MWSkillClass.MAGIC] = MWSkillEffectivity.IMMUNE
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