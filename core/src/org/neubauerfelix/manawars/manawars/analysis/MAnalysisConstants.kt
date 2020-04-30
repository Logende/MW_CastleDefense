package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.manawars.enums.MWArmorType
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass
import java.util.*

class MAnalysisConstants {

    companion object {

        const val SKILL_SHARE_VERTICAL = 0.15f
        const val SKILL_SHARE_HORIZONTAL = 1.0f - SKILL_SHARE_VERTICAL

        // used to determine worth of health of unit: if survival duration is higher, fix health is less important than attack per second
        // avg survival duration during fight
        const val UNIT_AVG_SURVIVAL_DURATION = 10f

        // average probability of a skill of a unit hitting an enemy
        const val UNIT_AVG_SKILL_HIT_PROBABILITY = 0.2f

        val ARMOR_TYPE_SHARES = EnumMap<MWArmorType, Float>(MWArmorType::class.java)
        init {
            ARMOR_TYPE_SHARES[MWArmorType.NONE] = 0.7f
            ARMOR_TYPE_SHARES[MWArmorType.ANTI_LIGHT] = 0.2f
            ARMOR_TYPE_SHARES[MWArmorType.ANTI_MAGIC] = 0.1f
            ARMOR_TYPE_SHARES[MWArmorType.SHIELD] = 0.0f
        }

        val ANIMATION_TYPE_SHARES = EnumMap<MWEntityAnimationType, Float>(MWEntityAnimationType::class.java)
        init {
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.HUMAN] = 0.85f
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.HUMAN_SHIELD] = 0.0f
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.MOUNT] = 0f
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.RIDER] = 0.15f
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.BUILDING] = 0f
        }

        val SKILL_CLASS_SHARES = EnumMap<MWSkillClass, Float>(MWSkillClass::class.java)
        init {
            SKILL_CLASS_SHARES[MWSkillClass.NORMAL] = 0.5f
            SKILL_CLASS_SHARES[MWSkillClass.MAGIC] = 0.25f
            SKILL_CLASS_SHARES[MWSkillClass.LIGHT] = 0.25f
            SKILL_CLASS_SHARES[MWSkillClass.SHIELD] = 0f
        }
    }

}