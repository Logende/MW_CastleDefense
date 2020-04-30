package org.neubauerfelix.manawars.manawars.entities.animation.building

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.animation.IBody
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

class BodyBuilding(val sized: ISized, val textureRegionAlive: TextureRegion, val textureRegionDead: TextureRegion,
                   val animation: Animation<TextureRegion>?):
        IBody, ISized by sized {

    var alive = true
    var playAnimation = false
    var stateTime = 0f
    var mirror = false
    override var paused: Boolean
        get() = false
        set(_) = throw NotImplementedError()

    init {
        if (animation != null) {
            animation.playMode = Animation.PlayMode.NORMAL
        }
    }

    override fun explode() {
    }

    override fun deadlyHit(killer: IMovable) {
        alive = false
    }

    override fun deadlyHit() {
        alive = false
    }

    override fun playEffect(effect: MWAnimationTypeBodyEffect?, weaponType: MWWeaponType?) {
        if (animation == null) {
            return
        }
        when (effect) {
            null -> playAnimation = false
            else -> {
                playAnimation = true
                stateTime = 0f
            }
        }
    }

    override fun updateAnimation(sized: ISized?) {
        if (sized is ILooking) {
            mirror = sized.direction == -1
        }
    }

    override val canFly: Boolean
        get() = false

    override val scale: Float
        get() = 1.0f



    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return MWCollisionType.TEXTURE
    }

    override fun draw(batcher: Batch) {
        batcher.color = color

        val drawX = if (mirror) x + width else x
        val drawWidth = if (mirror) -width else width

        if (playAnimation) {
            batcher.draw(animation!!.getKeyFrame(stateTime), drawX, y, drawWidth, height)
        } else {
            if (alive) {
                batcher.draw(textureRegionAlive, drawX, y, drawWidth, height)
            } else {
                batcher.draw(textureRegionDead, drawX, y, drawWidth, height)
            }
        }
        batcher.color = Color.WHITE
    }

    override fun doLogic(delta: Float) {
        if (playAnimation) {
            if (animation!!.isAnimationFinished(stateTime)) {
                playAnimation = false
            }
            stateTime += delta
        }
    }

    override fun drawDebugging(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x, y, width, height)
    }

    override val playingBodyEffect: Boolean
        get() = false

    override var color: Color = Color.WHITE
}
