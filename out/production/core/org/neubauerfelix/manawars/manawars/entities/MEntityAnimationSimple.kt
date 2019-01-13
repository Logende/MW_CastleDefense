package org.neubauerfelix.manawars.manawars.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.GameEntityMovable
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType

abstract class MEntityAnimationSimple(val animation: Animation<TextureRegion>, scale: Float, private val color: Color? = null,
                                      val rotationDuration: Float, playMode: PlayMode) :
        GameEntityMovable(animation.keyFrames[0].regionWidth * scale, animation.keyFrames[0].regionHeight * scale),
        IDrawable, ICollidable {


    var mirror: Boolean = false
    var isAnimationPaused: Boolean = false
    private var stateTime: Float = 0.toFloat()

    init {
        animation.playMode = playMode
        if (animation.keyFrames.size == 1) {
            animation.frameDuration = Float.MAX_VALUE
        }
    }


    fun setDrawRandomImageOnly() {
        isAnimationPaused = true
        stateTime = (Math.random() * animation.animationDuration).toFloat()
    }

    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        if (rotationDuration != 0f) {
            rotation = (stateTime % rotationDuration) / rotationDuration * 360f
        }
    }

    override fun draw(delta: Float, batcher: Batch) {
        if (!isAnimationPaused) {
            stateTime += delta
        }
        if (animation.playMode == PlayMode.NORMAL) {
            if (animation.isAnimationFinished(stateTime)) {
                finishedAnimation()
            }
        }
        if (color != null) {
            batcher.color = color
        }
        if (mirror) {
            batcher.draw(animation.getKeyFrame(stateTime, true), x + width, y, -width/2, height/2f,  -width, height, 1f, 1f, rotation)
        } else {
            batcher.draw(animation.getKeyFrame(stateTime, true), x, y, width/2, height/2f, width, height, 1f, 1f, rotation)
        }
        if (color != null) {
            batcher.color = Color.WHITE
        }
    }


    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return MWCollisionType.UNDEFINED
    }

    open fun finishedAnimation() {
        //Can be overidden
    }

}
