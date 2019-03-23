package org.neubauerfelix.manawars.manawars.entities.animation.castle

import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

class EntityAnimationProducerCastle(val textureName: String):
        IEntityAnimationProducer {

    override val bodyWidth: Int
        get() = MConstants.BODY_CASTLE_WIDTH

    override val bodyHeight: Int
        get() = MConstants.BODY_CASTLE_HEIGHT

    override val defaultScale: Float
        get() = 1f

    override fun produce(entity: ISized, scale: Float): IEntityAnimation {
        val body = BodyCastle(entity, MManaWars.m.getAssetLoader().createTextureRegion(textureName))
        return EntityAnimationAny(body, animationType)
    }

    override fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float): IEntity {
        val scale = Math.min(availableWidth / bodyWidth, availableHeight / bodyHeight)
        val offsetX = (availableWidth - bodyWidth * scale) / 2f
        val offsetY = (availableHeight - bodyHeight * scale) / 2f
        val rectangle = GameEntity( availableWidth, availableHeight)
        rectangle.setLocation(x + offsetX, y + offsetY)
        val body = BodyCastle(rectangle, MManaWars.m.getAssetLoader().createTextureRegion(textureName))
        return EntityAnimationAny(body, animationType)
    }


    override val animationType: MWEntityAnimationType = MWEntityAnimationType.CASTLE
}