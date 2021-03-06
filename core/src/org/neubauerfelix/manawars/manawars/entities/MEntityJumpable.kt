package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.entities.GameEntityMovable
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.events.EntityKnockbackEvent
import org.neubauerfelix.manawars.manawars.MConstants
import kotlin.math.abs


open class MEntityJumpable(width: Float, height: Float, override val knockbackFactor: Float) :
        GameEntityMovable(width, height), ILooking, IJumpable {


    override var direction = 1
        set(value){
            assert(abs(value) == 1)
            field = value
        }

    override var flying: Boolean = false

    final override var jumpsAmount: Int = 0
        private set

    final override var isOnGround = false
        private set
    final override var isKnockbacked = false
        private set


    override val isFalling: Boolean
        get() = speedY > 0

    override val isJumping: Boolean
        get() = speedY < 0 && jumpsAmount > 0


    override var speedX: Float
        get() = super.speedX
        set(value) {
            if(value * super.speedX < 0 && !isKnockbacked){ //new direction because of different algebraic signs
                this.direction = if (value > 0) { 1 } else { -1 }
            }
            super.speedX = value
        }


    fun pasteJumpable(e: IJumpable, onlyTemporaryValues: Boolean) {
        super.pasteMovable(e, onlyTemporaryValues)
        flying = e.flying
        jumpsAmount = e.jumpsAmount
        isOnGround = e.isOnGround
        isKnockbacked = e.isKnockbacked
    }


    override fun doLogic(delta: Float) {
        super.doLogic(delta)

        if (!flying && !isOnGround && speedY > 0) {
            if (bottom >= GameConstants.WORLD_HEIGHT_UNITS) { //Reached ground
                bottom = GameConstants.WORLD_HEIGHT_UNITS
                landOnGround()
                return
            }
        }
    }

    override fun jump() {
        jumpsAmount++
        speedY = MConstants.JUMP_SPEED_DEFAULT
        gravity()
    }


    override fun knockback(power_x: Float, power_y: Float, source: IMovable): Boolean {
        return if (source is ILooking && abs(source.speedX) >= MConstants.KNOCKBACK_MIRROR_DIRECTION_MIN_SPEED) {
            knockback(power_x, power_y, source.direction)
        }else{
            knockback(power_x, power_y, if (source.centerHorizontal > this.centerHorizontal) -1 else 1)
        }
    }

    override fun knockback(power_x: Float, power_y: Float, direction: Int): Boolean {
        return knockback(power_x * direction, power_y)
    }

    /**
     * Causes knockback. While being knocked back the entity is unable to walk.
     * Includes the entity scale: Bigger entities suffer less knockback (proportional).
     * If the entity is on ground while knocked back the knockback counts as first jump.
     * @param powerX Knockback in x direction.
     * @param powerY Knockback in y direction.
     * @return `true` if it was possible to execute the knockback.
     */
    open fun knockback(powerX: Float, powerY: Float): Boolean {
        AManaWars.m.getEventHandler().callEvent(EntityKnockbackEvent(this, powerX, powerY))
        jumpsAmount = 1.coerceAtLeast(jumpsAmount) //Knockback counts as jump when being on the ground when knocked back!
        val strengthFactor = knockbackFactor / propertyScale // bigger entities suffer less knockback
        if (strengthFactor == 0f) {
            return false
        }

        if (!isKnockbacked) {
            isKnockbacked = true
            speedX = powerX * strengthFactor
            speedY = -powerY * strengthFactor
        } else { //Already knocked back: Add speed to existing knockback speed
            val sameDirHor = speedX > 0 == powerX > 0
            if (sameDirHor) {
                val dirHor = direction
                val speedHor = abs(speedX).coerceAtLeast(abs(powerX  * strengthFactor)) //Set speed to max of both speeds but do not add together
                speedX = speedHor * dirHor
            } else {
                speedX += powerX * strengthFactor
            }
            speedY = -speedY.coerceAtLeast(powerY * strengthFactor)
        }
        gravity()
        //setLockedMovementEnd(500)
        return true
    }





    /**
     * Triggers gravity: Causes the entity to fall down if it is not on the ground already.
     */
    fun gravity() {
        isOnGround = false
        accelerationY = MConstants.GRAVITY_ACCELERATION
    }


    /**
     * Automatically executed when the entity lands on the ground. Sets vertical speed and acceleration to `0` and stops knockback (if active).
     */
    open fun landOnGround() {
        //stopLockedMovement()
        speedY = 0f
        speedX = 0f
        accelerationY = 0f

        jumpsAmount = 0
        isOnGround = true
        isKnockbacked = false
    }


}
