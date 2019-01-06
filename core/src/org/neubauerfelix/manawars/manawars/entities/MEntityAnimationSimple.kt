package org.neubauerfelix.manawars.manawars.entities

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.GameEntityMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType

abstract class MEntityAnimationSimple(val animation: Animation<TextureRegion>, scale: Float, private val color: Color? = null) : GameEntityMovable(animation.keyFrames[0].regionWidth * scale, animation.keyFrames[0].regionHeight * scale), IDrawable, ICollidable {


    var mirror: Boolean = false
    var isAnimationPaused: Boolean = false
    private var stateTime: Float = 0.toFloat()


    fun setDrawRandomImageOnly() {
        isAnimationPaused = true
        stateTime = (Math.random() * animation.animationDuration).toFloat()
    }


    override fun draw(delta: Float, batcher: Batch) {
        if (!isAnimationPaused) {
            stateTime += delta
        }
        if (animation.playMode == PlayMode.NORMAL) {
            if (animation.isAnimationFinished(stateTime)) {
                newAnimationStart()
            }
            if (color != null) {
                batcher.color = color
            }
            if (mirror) {
                batcher.draw(animation.getKeyFrame(stateTime, true), x + width, y, -width, height)
            } else {
                batcher.draw(animation.getKeyFrame(stateTime, true), x, y, width, height)
            }
            if (color != null) {
                batcher.color = Color.WHITE
            }
        }
    }


    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return MWCollisionType.UNDEFINED
    }

    open fun newAnimationStart() {
        stateTime = 0f
        //Can be overidden
    }


}
