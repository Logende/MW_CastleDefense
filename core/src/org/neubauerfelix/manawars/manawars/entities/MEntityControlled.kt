package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.handlers.MathUtils
import java.util.*
import kotlin.math.abs
import kotlin.math.min


open class MEntityControlled(animationProducer: IEntityAnimationProducer,
                             knockbackFactor: Float,
                             health: Float,
                             action: IDataAction,
                             actionCooldown: Float,
                             stateMultipliers: Map<MWState, MWStateEffectivity> = EnumMap(MWState::class.java),
                             skillMultipliers: Map<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java),
                             skillDurabilityMultipliers: Map<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java),
                             drainMultiplier: Float = 0f,
                             armor: MWArmorType = MWArmorType.NONE,
                             override var walkSpeedMax: Float = MConstants.UNIT_AVG_WALK_SPEED_MAX,
                             override val walkAcceleration: Float = MConstants.UNIT_AVG_WALK_ACC,
                             override var controller: IController,
                             override val data: IDataUnit): // data is just a reference for CD classes which need data. It is not used for the entity properties
        MEntityUpgraded(animationProducer, knockbackFactor, health, action, actionCooldown, stateMultipliers, skillMultipliers,
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
            if (!goalX.isNaN() && canWalk()) {
                val offset = goalX - this.centerHorizontal
                val distance = abs(offset)
                if (distance > 10f) {
                    val slowingDistance = 200f
                    val rampedSpeed = this.walkSpeedMax * (distance / slowingDistance)
                    val clippedSpeed = min(rampedSpeed, walkSpeedMax)
                    val desiredVelocity = (clippedSpeed / distance) * offset
                    accelerationX = MathUtils.truncate((desiredVelocity - this.speedX), walkAcceleration)
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



    override fun draw(batcher: Batch) {
        super.draw(batcher)
        controller.drawAbove(batcher)
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