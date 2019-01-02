package org.neubauerfelix.manawars.manawars.entities.animation.human

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimation
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class EntityAnimationHuman(private val body: BodyHumanSmart): EntityAnimation(body, body.scale) {




    override fun playDeathAnimation(damager: IEntity?, cause: MWDamageCause) {
        when (cause) {
            MWDamageCause.ANIMATION -> this.body.explode()
            MWDamageCause.SKILL -> this.body.deadlyHit(damager as IMovable)
            MWDamageCause.STATEEFFECT, MWDamageCause.SUMMON_LIMIT_EXCEEDED, MWDamageCause.TRIBE_DISPOSED -> this.body.deadlyHit()
        }
    }

    override fun update() {
        body.updateAnimationType(true, true)
    }

    override fun draw(delta: Float, batcher: Batch) {
        body.draw(delta, batcher)
    }

    override fun getCollisionType(intersection: ISized): MWCollisionType {
        return body.getCollisionType(intersection)
    }
}