package org.neubauerfelix.manawars.manawars.entities.animation.rider

import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import kotlin.math.min

class EntityAnimationProducerRider(val producerMount: IEntityAnimationProducer, val producerHuman: IEntityAnimationProducer):
        IEntityAnimationProducer {

    override val bodyWidth: Int
        get() = MConstants.BODY_RIDER_WIDTH

    override val bodyHeight: Int
        get() = MConstants.BODY_RIDER_HEIGHT

    override val defaultScale: Float
        get() = 1f

    override fun produce(entity: ISized, scale: Float): IEntityAnimation {
        val body = BodyRider(entity, producerMount, producerHuman, scale, scale)
        return EntityAnimationAny(body, animationType)
    }

    override fun produce(x: Float, y: Float, availableWidth: Float, availableHeight: Float,
                         weaponType: MWWeaponType?): IEntity {
        val scale = min(availableWidth / bodyWidth, availableHeight / bodyHeight)
        val offsetX = (availableWidth - bodyWidth * scale) / 2f
        val offsetY = (availableHeight - bodyHeight * scale) / 2f
        val rectangle = GameEntity( availableWidth, availableHeight)
        rectangle.setLocation(x + offsetX, y + offsetY)
        val body = BodyRider(rectangle, producerMount, producerHuman, scale, scale)
        if (weaponType != null) {
            body.equipWeapon(weaponType)
        }
        body.updateAnimation(rectangle)
        return EntityAnimationAny(body, animationType)
    }


    override val animationType: MWEntityAnimationType = MWEntityAnimationType.RIDER
}