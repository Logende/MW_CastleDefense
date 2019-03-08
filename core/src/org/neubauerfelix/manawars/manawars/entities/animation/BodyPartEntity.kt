package org.neubauerfelix.manawars.manawars.entities.animation


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.MEntityJumpable

class BodyPartEntity(private val bodyPartData: IBodyPartData, private val sized: ISized, private val textureRegion: TextureRegion, duration: Int,
                     private val mirror: Boolean, private val maxRotationAngle: Float = MConstants.BODY_PART_DETACH_MAX_ROTATION_ANGLE,
                     private val bloodColor: Color) :
        MEntityJumpable(textureRegion.regionWidth.toFloat(), textureRegion.regionHeight.toFloat()), IDrawable {

    private var currentRotationSummand: Float = 0f
    private val despawnTime: Long = AManaWars.m.screen.getGameTime() + duration
    private var bloodTime: Long = 0


    fun startRotation(direction: Int = if(Math.random() >0.5) 1 else -1, startSpeed: Float = MConstants.BODY_PART_DETATCH_ROTATION_START_SPEED) {
        startRotation(startSpeed * direction.toFloat() * Math.random().toFloat())
    }


    private fun startRotation(rotation: Float) {
        setRotationOrigin((textureRegion.regionWidth / 2).toFloat(), 0f)
        currentRotationSummand = rotation
    }


    override fun draw(delta: Float, batcher: Batch) {
        batcher.draw(textureRegion, if (mirror) x+width else x, y,
                if (mirror) -originX else originX, originY,
                if (mirror) -width else width, height,
                1f, 1f,
                rotation * if (mirror) -1 else 1)
    }

    override fun doLogic(delta: Float) {
        super.doLogic(delta)

        val time = AManaWars.m.screen.getGameTime()
        if (time >= despawnTime) {
            remove = true
            return
        }

        if(time >= bloodTime && !this.isOnGround){
            bloodTime = time + MConstants.BODY_PART_BLOOD_DELAY
            MManaWars.m.getAnimationHandler().playBloodAnimation(this, bloodColor)
        }
        if (Math.abs(rotation) < maxRotationAngle && currentRotationSummand > 0.001) {
            currentRotationSummand *= 0.9f
        }
    }


    /**
     * Causes knockback.
     * @param speedX Horizontal knockback speed.
     * @param speedY Vertical knockback speed.
     * @param additional If `true` the knockback will be added to the existing speed of the entity,
     * else the speed will be completely overwritten.
     */
    fun knockback(speedX: Float, speedY: Float, additional: Boolean, rotation: Boolean = true) {
        val factor = textureRegion.regionWidth / bodyPartData.bodyData.bodyWidth
        this.speedX = (speedX * factor + if (additional) speedX else 0f)
        this.speedY = (speedY * factor + if (additional) speedY else 0f)
        gravity()
        if(rotation) {
            startRotation(if (speedX > 0) 1 else -1)
        }
    }

    override fun landOnGround() {
        super.landOnGround()
        speedX = 0f
        accelerationX = 0f
    }


}
