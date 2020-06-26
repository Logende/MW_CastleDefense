package org.neubauerfelix.manawars.manawars.entities.animation.pet

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.entities.animation.IBody
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

open class BodyPet(val animation: Animation<TextureRegion>, override val scale: Float, var sized: ISized): ICollidable, ISized by sized, IBody{

    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return MWCollisionType.PET
    }

    override fun explode() {
        return
    }

    override fun deadlyHit(killer: IMovable) {
        return
    }

    override fun deadlyHit() {
        return
    }

    override fun playEffect(effect: MWAnimationTypeBodyEffect?) {
        return
    }

    override fun equipWeapon(weaponType: MWWeaponType) {
        return
    }

    override fun drawDebugging(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x, y, width, height)
    }

    override fun updateAnimation(sized: ISized?) {
        return
    }

    override val canFly: Boolean = true
    override val playingBodyEffect: Boolean = false
    override var color: Color = Color.WHITE

    override var paused: Boolean =  false
    private var stateTime = 0f

    override fun draw(batcher: Batch) {
        batcher.draw(animation.getKeyFrame(stateTime), x, y, width, height)
    }

    override fun doLogic(delta: Float) {
        if (!paused) {
            stateTime += delta
        }
    }
}