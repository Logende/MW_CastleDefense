package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.*


open class MEntityControlled(animationProducer: IEntityAnimationProducer,
                             health: Float,
                             action: IDataAction,
                             actionCooldown: Float,
                             stateMultipliers: Map<MWState, MWStateEffectivity> = HashMap(),
                             skillMultipliers: Map<MWSkillClass, Float> = HashMap(),
                             skillDurabilityMultipliers: Map<MWSkillClass, Float> = HashMap(),
                             drainMultiplier: Float = 0f,
                             armor: MWArmorType = MWArmorType.NONE,
                             override val walkSpeedMax: Float = MConstants.UNIT_AVG_WALK_SPEED_MAX,
                             override val walkAcceleration: Float = MConstants.UNIT_AVG_WALK_ACC,
                             override var controller: IController,
                             override val data: IDataUnit): // data is just a reference for CD classes which need data. It is not used for the entity properties
        MEntityUpgraded(animationProducer, health, action, actionCooldown, stateMultipliers, skillMultipliers,
                skillDurabilityMultipliers, drainMultiplier, armor), IControlled {


    override var goalX: Float = Float.NaN


    override fun jump(maxJumps: Int): Boolean {
        if (jumpsAmount >= maxJumps) {
            return false
        }
        this.jump()
        return true
    }

    override fun lookTo(l: GameRectangle) {
        this.lookTo(if (centerHorizontal < l.centerHorizontal) 1 else -1)
    }

    override fun lookTo(directionNew: Int) {
        val directionOld = direction
        if (directionNew != directionOld) {
            direction = directionNew
        }
    }


    override fun doLogic(delta: Float) {
        controller.doLogic(delta)

        if (isOnGround && !this.isKnockbacked) {
            if (goalX != Float.NaN && canWalk()) {
                val offset = goalX - this.centerHorizontal
                val distance = Math.abs(offset)
                if (distance > 10f) {
                    val slowingDistance = 200f
                    val rampedSpeed = this.walkSpeedMax * (distance / slowingDistance)
                    val clippedSpeed = Math.min(rampedSpeed, walkSpeedMax)
                    val desiredVelocity = (clippedSpeed / distance) * offset
                    accelerationX = truncate((desiredVelocity - this.speedX), walkAcceleration)
                } else {
                    accelerationX = 0f
                    speedX = 0f
                }
            }
        }


        super.doLogic(delta)
    }

    override fun move(delta: Float) {
        super.move(delta)
        if (isOnGround && !this.isKnockbacked) {
            speedX = direction * Math.min(walkSpeedMax, Math.abs(speedX)) // truncate: do not allow more than max speed
        }
    }

    private fun truncate(v : Float, maxLength: Float): Float {
        return if (v > 0) {
            Math.min(v, maxLength)
        } else {
            Math.max(v, -maxLength)
        }
    }


    override fun draw(delta: Float, batcher: Batch) {
        super.draw(delta, batcher)
        controller.drawAbove(delta, batcher)
    }

    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        val death = super.damage(value, damager, cause)
        controller.damage(value, damager, cause)
        return death
    }

    override fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        val death = super.death(damager, cause)
        if (death) {
            controller.death(damager, cause)
        }
        return death
    }



}