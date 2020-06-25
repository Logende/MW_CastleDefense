package org.neubauerfelix.manawars.manawars.entities.animation

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding
import org.neubauerfelix.manawars.manawars.entities.animation.human.EntityAnimationProducerHuman
import org.neubauerfelix.manawars.manawars.entities.animation.mount.EntityAnimationProducerMount
import org.neubauerfelix.manawars.manawars.entities.animation.rider.EntityAnimationProducerRider
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import org.neubauerfelix.manawars.manawars.storage.Configuration

interface IEntityAnimationProducer: IBodyData {


    companion object {
        fun createProducerHuman(skinName: String): IEntityAnimationProducer{
            val bodyDataHuman = MManaWars.m.getBodyDataHandler().getBodyDataHuman(skinName)
            return EntityAnimationProducerHuman(bodyDataHuman)
        }
        fun createProducerMount(skinName: String): IEntityAnimationProducer{
            val bodyDataMount = MManaWars.m.getBodyDataHandler().getBodyDataMount(skinName)
            return EntityAnimationProducerMount(bodyDataMount)
        }
        fun createProducerRider(skinMount: String, skinHuman: String): IEntityAnimationProducer{
            val producerMount = createProducerMount(skinMount)
            val producerHuman = createProducerHuman(skinHuman)
            return EntityAnimationProducerRider(producerMount, producerHuman)
        }
        fun createProducerBuilding(textureNameAlive: String, textureNameDead: String = textureNameAlive,
                                   animationTextureName: String? = null,
                                   animationFrameDuration: Float = 0f): IEntityAnimationProducer{
            return EntityAnimationProducerBuilding(textureNameAlive, textureNameDead, animationTextureName,
                    animationFrameDuration)
        }


        fun createProducer(configText: String) : IEntityAnimationProducer {
            val animationParts = configText.split(":")
            val animationType = MWEntityAnimationType.valueOf(animationParts[0].toUpperCase())

            return when (animationType) {
                MWEntityAnimationType.HUMAN, MWEntityAnimationType.HUMAN_SHIELD -> {
                    createProducerHuman(animationParts[1])
                }
                MWEntityAnimationType.MOUNT -> {
                    createProducerMount(animationParts[1])
                }

                // first mount then human skin in arguments
                MWEntityAnimationType.RIDER -> {
                    createProducerRider(animationParts[1], animationParts[2])
                }
                MWEntityAnimationType.BUILDING -> {
                    throw NotImplementedError()
                }
            }
        }
    }

    fun produce(entity: ISized, scale: Float = defaultScale): IEntityAnimation
    fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float, weaponType: MWWeaponType?): IEntity


    val animationType: MWEntityAnimationType


}