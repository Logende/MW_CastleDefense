package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataUnitLoaded(override val name: String, config: Configuration) : DataUnit() {


    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("unit_${name}_name")


    override val animation: IEntityAnimationProducer
    override val armor: Map<MWArmorHolder, MWArmorType>

    init {
        val animationParts = config.getString("animation").split(":")
        val animationType = MWEntityAnimationType.valueOf(animationParts[0].toUpperCase())

        animation = when (animationType) {
            MWEntityAnimationType.HUMAN -> {
                IEntityAnimationProducer.createProducerHuman(animationParts[1])
            }
            MWEntityAnimationType.MOUNT -> {
                IEntityAnimationProducer.createProducerMount(animationParts[1])
            }

            // first mount then human skin in arguments
            MWEntityAnimationType.RIDER -> {
                IEntityAnimationProducer.createProducerRider(animationParts[1], animationParts[2])
            }
        }

        armor = when (animationType) {
            MWEntityAnimationType.HUMAN -> {
                hashMapOf(Pair(MWArmorHolder.HUMAN_BODY, getArmor(config, 0)),
                        Pair(MWArmorHolder.HUMAN_HEAD, getArmor(config, 1)))
            }
            MWEntityAnimationType.RIDER -> {
                hashMapOf(Pair(MWArmorHolder.MOUNT, getArmor(config, 0)),
                        Pair(MWArmorHolder.HUMAN_BODY, getArmor(config, 1)),
                        Pair(MWArmorHolder.HUMAN_HEAD, getArmor(config, 2)))
            }
            MWEntityAnimationType.MOUNT -> {
                hashMapOf(Pair(MWArmorHolder.MOUNT, getArmor(config, 0)))
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
    override val health: Float = config.getFloat("health") * MConstants.UNIT_AVG_HEALTH
    override val actionCooldown: Long = config.getLong("cooldown") * MConstants.UNIT_AVG_ACTION_COOLDOWN
    override val stateMultipliers: Map<MWState, MWStateEffectivity> = hashMapOf()
    override val skillMultipliers: Map<MWSkillClass, Float> = hashMapOf()
    override val skillDurabilityMultipliers: Map<MWSkillClass, Float> = hashMapOf()
    override val drainMultiplier: Float = config.getFloat("drain")

}