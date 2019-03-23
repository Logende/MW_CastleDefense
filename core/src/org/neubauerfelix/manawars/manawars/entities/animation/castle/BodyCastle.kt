package org.neubauerfelix.manawars.manawars.entities.animation.castle

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.animation.IBody
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

class BodyCastle(val sized: ISized, val textureRegion: TextureRegion):
        IBody, ISized by sized {



    override fun explode() {
    }

    override fun deadlyHit(killer: IMovable) {
    }

    override fun deadlyHit() {
    }

    override fun playEffect(effect: MWAnimationTypeBodyEffect?, weaponType: MWWeaponType?) {
    }

    override fun updateAnimation(sized: ISized?) {
    }

    override val canFly: Boolean
        get() = false

    override val scale: Float
        get() = 1.0f



    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return MWCollisionType.CASTLE
    }

    override fun draw(delta: Float, batcher: Batch) {
        batcher.color = color
        batcher.draw(textureRegion, x, y, width, height)
        batcher.color = Color.WHITE
    }

    override fun doLogic(delta: Float) {
        // TODO: If damaged show damaged animation
    }

    override fun drawDebugging(shapeRenderer: ShapeRenderer) {
        shapeRenderer.rect(x, y, width, height)
    }

    override val playingBodyEffect: Boolean
        get() = false

    override var color: Color = Color.WHITE
}
