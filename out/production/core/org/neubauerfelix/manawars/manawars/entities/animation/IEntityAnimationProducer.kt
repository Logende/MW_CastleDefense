package org.neubauerfelix.manawars.manawars.entities.animation

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.animation.human.EntityAnimationProducerHuman
import org.neubauerfelix.manawars.manawars.entities.animation.mount.EntityAnimationProducerMount
import org.neubauerfelix.manawars.manawars.entities.animation.rider.EntityAnimationProducerRider

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
    }

    fun produce(entity: ISized, scale: Float = defaultScale): IEntityAnimation
    fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float): IEntity


}