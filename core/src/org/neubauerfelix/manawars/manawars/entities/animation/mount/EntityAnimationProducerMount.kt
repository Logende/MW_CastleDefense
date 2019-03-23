package org.neubauerfelix.manawars.manawars.entities.animation.mount

import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

class EntityAnimationProducerMount(bodyDataMount: IBodyDataMount): IEntityAnimationProducer, IBodyDataMount by bodyDataMount{



    override fun produce(entity: ISized, scale: Float): IEntityAnimation {
        val body = BodyMountSmart(this, scale, entity)
        return EntityAnimationAny(body, animationType)
    }

    override fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float): IEntity {
        val scale = Math.min(availableWidth / bodyWidth, availableHeight / bodyHeight)
        val offsetX = (availableWidth - bodyWidth * scale) / 2f
        val offsetY = (availableHeight - bodyHeight * scale) / 2f
        val rectangle = GameEntity( availableWidth, availableHeight)
        rectangle.setLocation(x + offsetX, y + offsetY)
        val body = BodyMountSmart(this, scale, rectangle)
        return EntityAnimationAny(body, animationType)
    }

    override val animationType: MWEntityAnimationType = MWEntityAnimationType.MOUNT
}