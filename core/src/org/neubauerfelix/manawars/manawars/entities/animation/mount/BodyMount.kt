package org.neubauerfelix.manawars.manawars.entities.animation.mount


import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartAttached
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType

open class BodyMount(val bodyDataMount: IBodyDataMount, scale: Float, var sized: ISized): ICollidable, ISized by sized {


    var head: BodyPartAttached
    var body: BodyPartAttached
    var footBL: BodyPartAttached
    var footBR: BodyPartAttached
    var footFL: BodyPartAttached
    var footFR: BodyPartAttached
    var mirror: Boolean
        get() = head.mirror
        set(value) {
            head.setMirror(value)
            body.setMirror(value)
            footBL.setMirror(value)
            footBR.setMirror(value)
            footFL.setMirror(value)
            footFR.setMirror(value)
        }

    var scale: Float = scale
        protected set

    var latestHitBodyPart: BodyPartAttached? = null
        private set

    var color: Color = Color.WHITE
        set(c) {
            this.color = c
            head.setColor(c)
            body.setColor(c)
            footBL.setColor(c)
            footBL.setColor(c)
            footFL.setColor(c)
            footFR.setColor(c)
        }

    var bodyPartsAttached = true
        protected set

    init {
        head = BodyPartAttached(bodyDataMount.head, scale)
        body = BodyPartAttached(bodyDataMount.body, scale)
        footBL = BodyPartAttached(bodyDataMount.footBL, scale)
        footBR = BodyPartAttached(bodyDataMount.footBR, scale)
        footFL = BodyPartAttached(bodyDataMount.footFL, scale)
        footFR = BodyPartAttached(bodyDataMount.footFR, scale)
    }


    fun updateFeet(backLeftRot: Float, backRightRot: Float, frontLeftRot: Float, frontRightRot: Float) {
        footBL.update(backLeftRot)
        footBR.update(backRightRot)
        footFL.update(frontLeftRot)
        footFR.update(frontRightRot)
    }

    override fun getCollisionType(other: ISized): MWCollisionType {
        if (body.overlaps(other)) {
            latestHitBodyPart = body
            return MWCollisionType.MOUNT_BODY
        }
        if (head.overlaps(other)) {
            latestHitBodyPart = head
            return MWCollisionType.MOUNT_HEAD
        }
        if (footBL.overlaps(other)) {
            latestHitBodyPart = footBL
            return MWCollisionType.MOUNT_FOOT
        }
        if (footBR.overlaps(other)) {
            latestHitBodyPart = footBR
            return MWCollisionType.MOUNT_FOOT
        }
        if (footFL.overlaps(other)) {
            latestHitBodyPart = footFL
            return MWCollisionType.MOUNT_FOOT
        }
        if (footFR.overlaps(other)) {
            latestHitBodyPart = footFR
            return MWCollisionType.MOUNT_FOOT
        }

        return MWCollisionType.NONE
    }

    fun deadlyHit() {
        assert(bodyPartsAttached)
        val sized = this.sized
        head.detach(sized)
        body.detach(sized)
        footBL.detach(sized)
        footBR.detach(sized)
        footFL.detach(sized)
        footFR.detach(sized)
        bodyPartsAttached = false
    }


    fun deadlyHit(killer: IMovable) {
        assert(bodyPartsAttached)
        val sized = this.sized
        if (latestHitBodyPart != null) {
            val ySpeedOffset = (-200 - if (latestHitBodyPart == head) 400 else 0).toFloat()
            latestHitBodyPart!!.detach(sized, killer.speedX * 0.5f, killer.speedY + ySpeedOffset)
        }
        head.detach(sized, killer, 0.5f, 0.5f)
        body.detach(sized, killer, 0.5f, 0.5f)
        footBL.detach(sized, killer, 0.5f, 0.5f)
        footBR.detach(sized, killer, 0.5f, 0.5f)
        footFL.detach(sized, killer, 0.5f, 0.5f)
        footFR.detach(sized, killer, 0.5f, 0.5f)
        bodyPartsAttached = false
    }

    fun explode() {
        assert(bodyPartsAttached)
        val sized = this.sized
        head.detach(sized, Math.random().toFloat() * 400 - 200, (-Math.random()).toFloat() * 300 - 2000)
        body.detach(sized, Math.random().toFloat() * 2000 - 1000, Math.random().toFloat() * 2000 - 1000)
        footBL.detach(sized, (-Math.random()).toFloat() * 1000 - 1100, Math.random().toFloat() * 600 - 1000)
        footBR.detach(sized, (+Math.random()).toFloat() * 1000 + 1100, Math.random().toFloat() * 600 - 1000)
        footFL.detach(sized, (-Math.random()).toFloat() * 1000 - 1100, Math.random().toFloat() * 600 - 1000)
        footFR.detach(sized, (+Math.random()).toFloat() * 1000 + 1100, Math.random().toFloat() * 600 - 1000)
        bodyPartsAttached = false
    }

    /**
     * Causes all body parts to re-attach. Requires them to be detached in first place.
     */
    fun reattachAll() {
        head.reattach(true, 0f)
        body.reattach(true, 0f)
        footBL.reattach(true, 0f)
        footBR.reattach(true, 0f)
        footFL.reattach(true, 0f)
        footFR.reattach(true, 0f)
        bodyPartsAttached = true
    }

}
