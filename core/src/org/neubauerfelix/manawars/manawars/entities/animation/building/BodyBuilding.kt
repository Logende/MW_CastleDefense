package org.neubauerfelix.manawars.manawars.entities.animation.building

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingAction
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
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
    }

    override val canFly: Boolean
        get() = false

    override val scale: Float
        get() = 1.0f



    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return MWCollisionType.TEXTURE
    }

    override fun draw(delta: Float, batcher: Batch) {
        batcher.color = color

        if (playAnimation) {
            batcher.draw(animation!!.getKeyFrame(stateTime), x, y, width, height)
            stateTime += delta
            if (animation!!.isAnimationFinished(stateTime)) {
                playAnimation = false
            }
        } else {
            if (alive) {
                batcher.draw(textureRegionAlive, x, y, width, height)
            } else {
                batcher.draw(textureRegionDead, x, y, width, height)
            }
        }
        batcher.color = Color.WHITE
    }

    override fun doLogic(delta: Float) {
    }

    override fun drawDebugging(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x, y, width, height)
    }

    override val playingBodyEffect: Boolean
        get() = false

    override var color: Color = Color.WHITE
}
