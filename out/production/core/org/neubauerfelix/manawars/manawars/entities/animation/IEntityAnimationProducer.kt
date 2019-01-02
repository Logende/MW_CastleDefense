package org.neubauerfelix.manawars.manawars.entities.animation

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IAnimated
import org.neubauerfelix.manawars.manawars.entities.animation.human.EntityAnimationProducerHuman

interface IEntityAnimationProducer: IBodyData {


    companion object {
        fun createProducerHuman(skinName: String): IEntityAnimationProducer{
            val bodyDataHuman = MManaWars.m.getBodyDataHandler().getBodyDataHuman(skinName)
            return EntityAnimationProducerHuman(bodyDataHuman)
        }
    }

    fun produce(entity: IAnimated, scale: Float = defaultScale): IEntityAnimation
    fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float): IEntity

    val width: Float
    val height: Float

}