package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.IUnitAnalysis
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataUnitLoaded(override val name: String, config: Configuration, val league: IDataLeague) : DataUnit() {


    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("unit_${name}_name")
    override val boss: Boolean = config.getBoolean("boss")


    override val animation: IEntityAnimationProducer
    override val armor: Map<MWArmorHolder, MWArmorType>

    init {
        val animationParts = config.getString("animation").split(":")
        val animationType = MWEntityAnimationType.valueOf(animationParts[0].toUpperCase())

        animation = when (animationType) {
            MWEntityAnimationType.HUMAN, MWEntityAnimationType.HUMAN_SHIELD -> {
                IEntityAnimationProducer.createProducerHuman(animationParts[1])
            }
            MWEntityAnimationType.MOUNT -> {
                IEntityAnimationProducer.createProducerMount(animationParts[1])
            }

            // first mount then human skin in arguments
            MWEntityAnimationType.RIDER -> {
                IEntityAnimationProducer.createProducerRider(animationParts[1], animationParts[2])
            }
            MWEntityAnimationType.CASTLE -> {
                IEntityAnimationProducer.createProducerCastle(animationParts[1])
            }
        }

        armor = when (animationType) {
            MWEntityAnimationType.HUMAN -> {
                hashMapOf(Pair(MWArmorHolder.HUMAN_BODY, getArmor(config, 0)),
                        Pair(MWArmorHolder.HUMAN_HEAD, getArmor(config, 1)))
            }
            MWEntityAnimationType.HUMAN_SHIELD -> {
                hashMapOf(Pair(MWArmorHolder.HUMAN_BODY, getArmor(config, 0)),
                        Pair(MWArmorHolder.HUMAN_HEAD, getArmor(config, 1)),
                        Pair(MWArmorHolder.SHIELD, MWArmorType.SHIELD))
            }
            MWEntityAnimationType.RIDER -> {
                hashMapOf(Pair(MWArmorHolder.MOUNT, getArmor(config, 0)),
                        Pair(MWArmorHolder.HUMAN_BODY, getArmor(config, 1)),
                        Pair(MWArmorHolder.HUMAN_HEAD, getArmor(config, 2)))
            }
            MWEntityAnimationType.MOUNT -> {
                hashMapOf(Pair(MWArmorHolder.MOUNT, getArmor(config, 0)))
            }
            MWEntityAnimationType.CASTLE -> {
                hashMapOf()
            }
        }

    }

    private fun getArmor(config: Configuration, index: Int) : MWArmorType {
        return if (!config.contains("armor")) {
            MWArmorType.NONE
        } else {
            val armorParts = config.getString("armor").split(":")
            if (armorParts.size <= index) {
                MWArmorType.NONE
            } else {
                MWArmorType.valueOf(armorParts[index].toUpperCase())
            }
        }
    }


    override val action: IDataAction = MManaWars.m.getActionHandler().getAction(config.getString("action"))!!
    override var health: Float = config.getFloat("health")

    override var actionCooldown: Float = config.getFloat("cooldown") * MConstants.UNIT_AVG_ACTION_COOLDOWN
    override val stateMultipliers: MutableMap<MWState, MWStateEffectivity> = hashMapOf()
    override val skillMultipliers: MutableMap<MWSkillClass, Float> = hashMapOf()
    override val skillDurabilityMultipliers: MutableMap<MWSkillClass, Float> = hashMapOf()
    override var drainMultiplier: Float = config.getFloat("drain")

    override var walkSpeedMax: Float = if (config.contains("walkSpeedMax")) {
        config.getFloat("walkSpeedMax", 1f) * MConstants.UNIT_AVG_WALK_SPEED_MAX
    } else {
        if (animation.animationType == MWEntityAnimationType.RIDER) {
            MConstants.UNIT_AVG_WALK_SPEED_MAX * 2f
        } else {
            MConstants.UNIT_AVG_WALK_SPEED_MAX * 1f
        }
    }

    override var walkAcceleration: Float = if (config.contains("walkAcceleration")) {
        config.getFloat("walkAcceleration", 1f) * MConstants.UNIT_AVG_WALK_ACC
    } else {
        if (animation.animationType == MWEntityAnimationType.RIDER) {
            MConstants.UNIT_AVG_WALK_ACC * 2f
        } else {
            MConstants.UNIT_AVG_WALK_ACC * 1f
        }
    }

    override var analysis: IUnitAnalysis = MManaWars.m.getUnitAnalysisHandler().loadUnitAnalysis(this)
        private set


    fun analyseUnit() {
        this.analysis = MManaWars.m.getUnitAnalysisHandler().analyse(this, league)
    }
}
