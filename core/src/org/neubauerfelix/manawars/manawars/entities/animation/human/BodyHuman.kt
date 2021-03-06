package org.neubauerfelix.manawars.manawars.entities.animation.human


import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartAttached
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

open class BodyHuman(val bodyDataHuman: IBodyDataHuman, scale: Float, val sized: ISized): ICollidable, ISized by sized {


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
            head.mirror = value
            body.mirror = value
            armL.mirror = value
            armR.mirror = value
            footL.mirror = value
            footR.mirror = value
            if (shield != null) {
                shield.mirror = value
            }
            if (weapon != null) {
                weapon!!.mirror = value
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
            field = c
            head.color = c
            body.color = c
            armL.color = c
            armR.color = c
            footL.color = c
            footR.color = c
            if (shield != null) {
                shield.color = c
            }
            if (weapon != null) {
                weapon!!.color = c
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
            this.shield = BodyPartAttached(bodyDataHuman.shield!!, scale * bodyDataHuman.shield!!.scale)
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
                this.weapon!!.mirror = this.mirror
                this.weapon!!.rotation = this.rotation
            }
        } else {
            //Do not remove weapon here: It does not harm to keep it and it might be re-used.
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


    override fun getCollisionType(intersection: ISized): MWCollisionType {
        if (this.shield != null && this.shield.enabled) {
            if (ISized.overlaps(shield, intersection)) {
                latestHitBodyPart = shield
                return MWCollisionType.SHIELD
            }
        }
        if (ISized.overlaps(body, intersection)) {
            latestHitBodyPart = body
            return MWCollisionType.HUMAN_BODY
        }
        if (ISized.overlaps(head, intersection)) {
            latestHitBodyPart = head
            return MWCollisionType.HUMAN_HEAD
        }
        if (ISized.overlaps(footL, intersection)) {
            latestHitBodyPart = footL
            return MWCollisionType.HUMAN_FOOT
        }
        if (ISized.overlaps(footR, intersection)) {
            latestHitBodyPart = footR
            return MWCollisionType.HUMAN_FOOT
        }

        return MWCollisionType.NONE
    }

    fun deadlyHit() {
        assert(bodyPartsAttached)
        head.detach()
        body.detach()
        armL.detach()
        armR.detach()
        footL.detach()
        footR.detach()
        if (shield != null && shield.enabled) {
            shield.detach()
        }
        bodyPartsAttached = false
    }


    fun deadlyHit(killer: IMovable) {
        assert(bodyPartsAttached)
        if (latestHitBodyPart != null) {
            val ySpeedOffset = (-200 - if (latestHitBodyPart == head) 400 else 0).toFloat()
            latestHitBodyPart!!.detach(killer.speedX * 0.5f, killer.speedY + ySpeedOffset)
        }

        if (head != latestHitBodyPart)
        head.detach(killer, 0.5f, 0.5f)

        if (body != latestHitBodyPart)
        body.detach(killer, 0.5f, 0.5f)

        if (armL != latestHitBodyPart)
        armL.detach(killer, 0.5f, 0.5f)

        if (armR != latestHitBodyPart)
        armR.detach(killer, 0.5f, 0.5f)

        if (footL != latestHitBodyPart)
        footL.detach(killer, 0.5f, 0.5f)

        if (footR != latestHitBodyPart)
        footR.detach(killer, 0.5f, 0.5f)

        if (shield != null && shield.enabled && shield != latestHitBodyPart) {
            shield.detach(killer, 0.5f, 0.5f)
        }
        bodyPartsAttached = false
    }

    fun explode() {
        assert(bodyPartsAttached)
        val sized = this.sized
        head.detach(Math.random().toFloat() * 400 - 200, (-Math.random()).toFloat() * 300 - 2000)
        body.detach(Math.random().toFloat() * 2000 - 1000, Math.random().toFloat() * 2000 - 1000)
        armL.detach((-Math.random()).toFloat() * 1000 - 1100, Math.random().toFloat() * 600 - 1000)
        armR.detach((+Math.random()).toFloat() * 1000 + 1100, Math.random().toFloat() * 600 - 1000)
        footL.detach((-Math.random()).toFloat() * 1000 - 1100, Math.random().toFloat() * 600 - 1000)
        footR.detach((+Math.random()).toFloat() * 1000 + 1100, Math.random().toFloat() * 600 - 1000)
        if (shield != null) {
            if(sized is IMovable){
                shield.detach(10f, 10f)
            }else{
                shield.detach(10f, 10f)
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
        shield?.reattach(false, 0f)
        bodyPartsAttached = true
    }
}


