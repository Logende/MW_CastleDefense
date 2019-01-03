package org.neubauerfelix.manawars.manawars.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

open class MEntityAnimated(animationProducer: IEntityAnimationProducer, health: Float) : MEntityLiving(animationProducer.width * animationProducer.defaultScale, animationProducer.height * animationProducer.defaultScale, health), IAnimated, ICollidable {


    private var colorRestoreTime: Long = -1L
    private var needsAnimationUpdate: Boolean = false
    override val animation = animationProducer.produce(this)
    init {
        propertyScale = animation.scale
    }

    var animationPaused: Boolean
        get() = this.animation.paused
        set(value) {
            this.animation.paused = value
        }


    override fun draw(delta: Float, batcher: Batch) {
        if (colorRestoreTime != -1L && AManaWars.m.screen.getGameTime() > colorRestoreTime) {
            colorRestoreTime = -1
            animation.color = Color.WHITE
        }
        if(needsAnimationUpdate){
            animation.update()
            needsAnimationUpdate = false
        }
        this.animation.draw(delta, batcher)
    }

    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return animation.getCollisionType(intersection)
    }

    override fun playBloodAnimation() {
        super.playBloodAnimation()
        animation.color = bloodColor
        colorRestoreTime = AManaWars.m.screen.getGameTime() + MConstants.HIT_RED_BLINK_DURATION
        MManaWars.m.getAnimationHandler().playBloodAnimation(this, bloodColor)
    }


    override fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        if(super.death(damager, cause)){
            animation.color = bloodColor
            animation.playDeathAnimation(damager, cause)
            return true
        }
        return false
    }

    override var speedX: Float
        get() = super.speedX
        set(value) {
            if((super.speedX == 0f && value != 0f) || (super.speedX != 0f && value == 0f)){ //started/stopped moving
                needsAnimationUpdate = true
            }
            super.speedX = value
        }

    override fun knockback(power_x: Float, power_y: Float): Boolean {
        val success = super.knockback(power_x, power_y)
        if (success) {
            needsAnimationUpdate = true
        }
        return success
    }

    override fun jump() {
        super.jump()
        needsAnimationUpdate = true
    }

    override fun landOnGround() {
        super.landOnGround()
        needsAnimationUpdate = true
    }

    override var direction = 1
        set(value){
            if(value != direction){
                needsAnimationUpdate = true
            }
            super.direction = value
        }



}
