package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.Color
import java.util.*

enum class MWArmorType(val color: Color, val walkSpeedFactor: Float) { //Keep those as simple as possible!

    NONE(MWSkillClass.NORMAL.color, 1f) {
        override fun createSkillEffectivity(): EnumMap<MWSkillClass, MWSkillEffectivity> {
            return EnumMap(MWSkillClass::class.java)
        }
    },

    ANTI_LIGHT(MWSkillClass.LIGHT.color, 0.75f) {
        override fun createSkillEffectivity(): EnumMap<MWSkillClass, MWSkillEffectivity> {
            val map = EnumMap<MWSkillClass, MWSkillEffectivity>(MWSkillClass::class.java)
            map[MWSkillClass.LIGHT] = MWSkillEffectivity.IMMUNE
            return map
        }
    },

    SHIELD(Color.BLACK, -1f) {
        override fun createSkillEffectivity(): EnumMap<MWSkillClass, MWSkillEffectivity> {
            val map = EnumMap<MWSkillClass, MWSkillEffectivity>(MWSkillClass::class.java)
            map[MWSkillClass.LIGHT] = MWSkillEffectivity.IMMUNE
            map[MWSkillClass.MAGIC] = MWSkillEffectivity.IMMUNE
            //map[MWSkillClass.NORMAL] = MWSkillEffectivity.WEAK
            return map
        }
    },

    ANTI_MAGIC(MWSkillClass.MAGIC.color, 0.75f) {
        override fun createSkillEffectivity(): EnumMap<MWSkillClass, MWSkillEffectivity> {
            val map = EnumMap<MWSkillClass, MWSkillEffectivity>(MWSkillClass::class.java)
            map[MWSkillClass.MAGIC] = MWSkillEffectivity.IMMUNE
            return map
        }
    };


    val skillEffectivities = createSkillEffectivity()

    protected abstract fun createSkillEffectivity(): EnumMap<MWSkillClass, MWSkillEffectivity>

    fun getSkillEffectivity(skillclass: MWSkillClass): MWSkillEffectivity {
        return if (skillEffectivities.containsKey(skillclass)) {
            skillEffectivities[skillclass]!!
        } else MWSkillEffectivity.NORMAL
    }
}