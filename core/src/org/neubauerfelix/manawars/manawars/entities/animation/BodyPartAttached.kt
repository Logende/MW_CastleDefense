package org.neubauerfelix.manawars.manawars.entities.animation


import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.MConstants

/**
 * This kind of body part can be either attached to the human body body or detached (for example when the entity is killed).
 * @author Felix Neubauer
 */
class BodyPartAttached(bodyPartData: IBodyPartData, scale: Float) : BodyPart(bodyPartData, scale) {


    var attached = true
        private set

    fun detach(knockbackSpeedX: Float = 0f, knockbackSpeedY: Float = 0f): BodyPartEntity {
        return this.detach(knockbackSpeedX, knockbackSpeedY, MConstants.RANDOM_ANIMATION_DESPAWN_TIME)
    }

    fun detach(knockbackFactorX: Float, knockbackFactorY: Float, despawnTime: Int): BodyPartEntity {
        val e = detach(despawnTime)
        e.knockback(knockbackFactorX, knockbackFactorY, false, true)
        return e
    }

    fun detach(cause: IMovable, knockbackFactorX: Float, knockbackFactorY: Float): BodyPartEntity {
        return this.detach(cause, knockbackFactorX, knockbackFactorY, MConstants.RANDOM_ANIMATION_DESPAWN_TIME)
    }

    fun detach(cause: IMovable?, knockbackFactorX: Float, knockbackFactorY: Float, despawnTime: Int): BodyPartEntity {
        val e = detach(despawnTime)
        if (cause != null) {
            e.knockback(cause.speedX * knockbackFactorX, cause.speedY * knockbackFactorY, true)
        }
        return e
    }

    private fun detach(despawnTime: Int): BodyPartEntity {
        check(attached)
        attached = false

        val e = BodyPartEntity(bodyPartData, this, tr, despawnTime, mirror, bloodColor = this.color)
        e.x = this.x
        e.y = this.y

        val skinCentreX = bodyPartData.bodyData.bodyWidth / 2
        val skinCentreY = bodyPartData.bodyData.bodyHeight / 2

        val bodyPartCentreX = relativeX + width / 2
        val bodyPartCentreY = relativeY + height / 2

        val knockbackX = -(bodyPartCentreX - skinCentreX) * MConstants.BODY_PART_DETACH_KNOCKBACK_FACTOR
        val knockbackY = (bodyPartCentreY - skinCentreY) * MConstants.BODY_PART_DETACH_KNOCKBACK_FACTOR
        e.knockback(knockbackX, knockbackY, false)
        e.startRotation()

        if (enabled) {
            e.spawn()
        }

        enabled = false
        return e
    }

    fun reattach(enable: Boolean, rotation: Float) {
        assert(!attached)
        attached = true
        this.rotation = rotation
        enabled = enable
    }


}
