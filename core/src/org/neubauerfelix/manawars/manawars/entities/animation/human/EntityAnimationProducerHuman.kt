package org.neubauerfelix.manawars.manawars.entities.animation.human

import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

class EntityAnimationProducerHuman(bodyDataHuman: IBodyDataHuman): IEntityAnimationProducer, IBodyDataHuman by bodyDataHuman{


    override fun produce(entity: ISized, scale: Float): IEntityAnimation {
        val body = BodyHumanSmart(this, entity, scale)
        return EntityAnimationAny(body, animationType)
    }

    override fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float): IEntity {
        val scale = Math.min(availableWidth / bodyWidth, availableHeight / bodyHeight)
        val offsetX = (availableWidth - bodyWidth * scale) / 2f
        val offsetY = (availableHeight - bodyHeight * scale) / 2f
        val rectangle = GameEntity( availableWidth, availableHeight)
        rectangle.setLocation(x + offsetX, y + offsetY)
        val body = BodyHumanSmart(this, rectangle, scale)
        return EntityAnimationAny(body, animationType)
    }

    override val animationType: MWEntityAnimationType = if (bodyDataHuman.shield != null ) {
        MWEntityAnimationType.HUMAN_SHIELD
    } else {
        MWEntityAnimationType.HUMAN
    }
}