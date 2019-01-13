package org.neubauerfelix.manawars.manawars.entities.animation.human


import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartAttached
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

open class BodyHuman(val bodyDataHuman: IBodyDataHuman, scale: Float, var sized: ISized): ICollidable, ISized by sized {


    var head: BodyPartAttached
    var body: BodyPartAttached
    var footL: BodyPartAttached
    var footR: BodyPartAttached
    var armL: BodyPartAttached
    var armR: BodyPartAttached
    val shield: BodyPartAttached?
    var mirror: Boolean
        get() = head.mirror
        set(value) {
            head.setMirror(value)
            body.setMirror(value)
            armL.setMirror(value)
            armR.setMirror(value)
            footL.setMirror(value)
            footR.setMirror(value)
            if (shield != null) {
                shield.setMirror(value)
            }
            if (weapon != null) {
                weapon!!.setMirror(value)
            }
        }

    var weapon: BodyPartAnimationWeapon? = null
        protected set
    var scale: Float = scale
        protected set

    var latestHitBodyPart: BodyPartAttached? = null
        private set

    var color: Color = Color.WHITE
        set(c) {
            this.color = c
            head.setColor(c)
            body.setColor(c)
            armL.setColor(c)
            armR.setColor(c)
            footL.setColor(c)
            footR.setColor(c)
            if (shield != null) {
                shield.setColor(c)
            }
            if (weapon != null) {
                weapon!!.setColor(c)
            }
        }


    var bodyPartsAttached = true
        protected set

    init {
        head = BodyPartAttached(bodyDataHuman.head, scale)
        body = BodyPartAttached(bodyDataHuman.body, scale)
        armL = BodyPartAttached(bodyDataHuman.armL, scale)
        armR = BodyPartAttached(bodyDataHuman.armR, scale)
        footL = BodyPartAttached(bodyDataHuman.footL, scale)
        footR = BodyPartAttached(bodyDataHuman.footR, scale)
        if(bodyDataHuman.shield != null){
            this.shield = BodyPartAttached(bodyDataHuman.shield!!, scale)
            this.shield.enabled = false
        }else{
            this.shield = null
        }
    }


    fun hasShield(): Boolean {
        return this.shield != null
    }

    /**
     * Enables/Disables the shield if one is existing. Disabled shields are hidden and excluded from collisions.
     * @param b Enable state.
     */
    fun setShieldEnabled(b: Boolean) {
        val shield = this.shield
        if (shield != null) {
            shield.enabled = b
        }
    }

    /**
     * Updates the weapon of this human body.
     * @param weapon New weapon Can be `null`.
     */
    fun setWeapon(weaponType: MWWeaponType?) {
        val currentWeapon = this.weapon
        if (weaponType != null) {
            if (currentWeapon == null || weaponType !== currentWeapon.weaponType) { //Only update weapon if it is different from the current one
                this.weapon = weaponType.createBodyPart(bodyDataHuman, this.scale)
            }
        } else {
            //Do not remove weapon here: It does  not harm to keep it and it might be re-used.
        }
    }


    fun updateArms(leftX: Float, leftY: Float, leftRot: Float, rightX: Float, rightY: Float, rightRot: Float) {
        armL.update(leftX, leftY, leftRot)
        armR.update(rightX, rightY, rightRot)
    }

    fun updateArms(leftRot: Float, rightRot: Float) {
        armL.update(leftRot)
        armR.update(rightRot)
    }


    override fun getCollisionType(other: ISized): MWCollisionType {
        if (this.shield != null) {
            if (shield.overlaps(other)) {
                latestHitBodyPart = shield
                return MWCollisionType.SHIELD
            }
        }
        if (body.overlaps(other)) {
            latestHitBodyPart = body
            return MWCollisionType.HUMAN_BODY
        }
        if (head.overlaps(other)) {
            latestHitBodyPart = head
            return MWCollisionType.HUMAN_HEAD
        }
        if (footL.overlaps(other)) {
            latestHitBodyPart = footL
            return MWCollisionType.HUMAN_FOOT
        }
        if (footR.overlaps(other)) {
            latestHitBodyPart = footR
            return MWCollisionType.HUMAN_FOOT
        }

        return MWCollisionType.NONE
    }

    fun deadlyHit() {
        assert(bodyPartsAttached)
        val sized = this.sized
        head.detach(sized)
        body.detach(sized)
        armL.detach(sized)
        armR.detach(sized)
        footL.detach(sized)
        footR.detach(sized)
        if (shield != null && shield.enabled) {
            shield.detach(sized)
        }
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
        armL.detach(sized, killer, 0.5f, 0.5f)
        armR.detach(sized, killer, 0.5f, 0.5f)
        footL.detach(sized, killer, 0.5f, 0.5f)
        footR.detach(sized, killer, 0.5f, 0.5f)
        if (shield != null && shield.enabled) {
            shield.detach(sized, killer, 0.5f, 0.5f)
        }
        bodyPartsAttached = false
    }

    fun explode() {
        assert(bodyPartsAttached)
        val sized = this.sized
        head.detach(sized, Math.random().toFloat() * 400 - 200, (-Math.random()).toFloat() * 300 - 2000)
        body.detach(sized, Math.random().toFloat() * 2000 - 1000, Math.random().toFloat() * 2000 - 1000)
        armL.detach(sized, (-Math.random()).toFloat() * 1000 - 1100, Math.random().toFloat() * 600 - 1000)
        armR.detach(sized, (+Math.random()).toFloat() * 1000 + 1100, Math.random().toFloat() * 600 - 1000)
        footL.detach(sized, (-Math.random()).toFloat() * 1000 - 1100, Math.random().toFloat() * 600 - 1000)
        footR.detach(sized, (+Math.random()).toFloat() * 1000 + 1100, Math.random().toFloat() * 600 - 1000)
        if (shield != null) {
            if(sized is IMovable){
                shield.detach(sized, sized , 10f, 10f)
            }else{
                shield.detach(sized , 10f, 10f)
            }
        }
        bodyPartsAttached = false
    }

    /**
     * Causes all body parts to re-attach. Requires them to be detached in first place.
     */
    fun reattachAll() {
        head.reattach(true, 0f)
        body.reattach(true, 0f)
        armL.reattach(true, 0f)
        armR.reattach(true, 0f)
        footL.reattach(true, 0f)
        footR.reattach(true, 0f)
        if (shield != null) {
            shield.reattach(false, 0f)
        }
        bodyPartsAttached = true
    }
}


