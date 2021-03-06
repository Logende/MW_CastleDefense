package org.neubauerfelix.manawars.manawars.entities.animation

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.*

class EntityAnimationAny(val body: IBody, override val entityAnimationType: MWEntityAnimationType) :
        EntityAnimation(body, body.scale) {


    override var paused: Boolean
        get() = super.paused
        set(value) {
            super.paused = value
            body.paused = paused
        }


    override fun playDeathAnimation(damager: IEntity?, cause: MWDamageCause) {
        when (cause) {
            MWDamageCause.ANIMATION -> this.body.explode()
            MWDamageCause.SKILL -> this.body.deadlyHit(damager as IMovable)
            MWDamageCause.STATEEFFECT, MWDamageCause.SUMMON_LIMIT_EXCEEDED, MWDamageCause.TRIBE_DISPOSED -> this.body.deadlyHit()
        }
    }

    override fun playBodyEffect(effect: MWAnimationTypeBodyEffect?) {
        this.body.playEffect(effect)
    }

    override fun equipWeapon(weaponType: MWWeaponType) {
        this.body.equipWeapon(weaponType)
    }

    override fun updateAnimation(sized: ISized?) {
        body.updateAnimation(sized)
    }

    override fun draw(batcher: Batch) {
        body.draw(batcher)
    }

    override fun doLogic(delta: Float) {
        body.doLogic(delta)
    }

    override fun drawDebugging(shapeRenderer: ShapeRenderer) {
        body.drawDebugging(shapeRenderer)
    }

    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return body.getCollisionType(intersection)
    }

    override val canFly: Boolean
        get() = false

    override val playingBodyEffect: Boolean
        get() = body.playingBodyEffect


    override var color: Color = Color.WHITE
        set(value) {
            field = value
            body.color = value
        }

    override fun destroyed() {
    }
}