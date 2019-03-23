package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.manawars.enums.MWArmorHolder
import org.neubauerfelix.manawars.manawars.enums.MWArmorType
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass

class MAnalysisConstants {

    companion object {

        const val SKILL_SHARE_VERTICAL = 0.15f
        const val SKILL_SHARE_HORIZONTAL = 1.0f - SKILL_SHARE_VERTICAL

        // used to determine worth of health of unit: if survival duration is higher, fix health is less important than attack per second
        // avg survival duration during fight
        const val UNIT_AVG_SURVIVAL_DURATION = 10f

        val ARMOR_TYPE_SHARES = HashMap<MWArmorType, Float>()
        init {
            ARMOR_TYPE_SHARES[MWArmorType.NONE] = 0.7f
            ARMOR_TYPE_SHARES[MWArmorType.ANTI_LIGHT] = 0.1f
            ARMOR_TYPE_SHARES[MWArmorType.ANTI_MAGIC] = 0.1f
            ARMOR_TYPE_SHARES[MWArmorType.SHIELD] = 0.1f
        }
        val ARMOR_HOLDER_HIT_SHARES = HashMap<MWEntityAnimationType, HashMap<MWArmorHolder, Float>>()
        init {
            val hitSharesHuman = HashMap<MWArmorHolder, Float>()
            hitSharesHuman[MWArmorHolder.HUMAN_BODY] = 0.6f
            hitSharesHuman[MWArmorHolder.HUMAN_HEAD] = 0.4f
            ARMOR_HOLDER_HIT_SHARES[MWEntityAnimationType.HUMAN] = hitSharesHuman

            val hitSharesHumanShield = HashMap<MWArmorHolder, Float>()
            hitSharesHumanShield[MWArmorHolder.HUMAN_BODY] = 0.05f
            hitSharesHumanShield[MWArmorHolder.HUMAN_HEAD] = 0.25f
            hitSharesHumanShield[MWArmorHolder.SHIELD] = 0.7f
            ARMOR_HOLDER_HIT_SHARES[MWEntityAnimationType.HUMAN_SHIELD] = hitSharesHumanShield

            val hitSharesRider = HashMap<MWArmorHolder, Float>()
            hitSharesRider[MWArmorHolder.HUMAN_BODY] = 0.2f
            hitSharesRider[MWArmorHolder.HUMAN_HEAD] = 0.3f
            hitSharesRider[MWArmorHolder.MOUNT] = 0.5f
            ARMOR_HOLDER_HIT_SHARES[MWEntityAnimationType.RIDER] = hitSharesRider
        }

        val ANIMATION_TYPE_SHARES = HashMap<MWEntityAnimationType, Float>()
        init {
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.HUMAN] = 0.8f
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.HUMAN_SHIELD] = 0.1f
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.MOUNT] = 0f
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.RIDER] = 0.1f
            ANIMATION_TYPE_SHARES[MWEntityAnimationType.CASTLE] = 0f
        }

        val SKILL_CLASS_SHARES = HashMap<MWSkillClass, Float>()
        init {
            SKILL_CLASS_SHARES[MWSkillClass.NORMAL] = 0.5f
            SKILL_CLASS_SHARES[MWSkillClass.MAGIC] = 0.25f
            SKILL_CLASS_SHARES[MWSkillClass.LIGHT] = 0.25f
            SKILL_CLASS_SHARES[MWSkillClass.SHIELD] = 0f
        }
    }

}