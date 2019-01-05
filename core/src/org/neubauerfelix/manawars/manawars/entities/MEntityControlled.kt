package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass
import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.enums.MWStateEffectivity
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.controller.IController


open class MEntityControlled(animationProducer: IEntityAnimationProducer,
                             health: Float, mana: Float,
                             actions: Array<IDataAction>,
                             manaRegen: Float,
                             stateMultipliers: Map<MWState, MWStateEffectivity> = HashMap(),
                             skillMultipliers: Map<MWSkillClass, Float> = HashMap(),
                             skillDurabilityMultipliers: Map<MWSkillClass, Float> = HashMap(),
                             drainMultiplier: Float = 0f,
                             override var controller: IController):
        MEntityUpgraded(animationProducer, health, mana, actions, manaRegen, stateMultipliers, skillMultipliers,
                skillDurabilityMultipliers, drainMultiplier), IControlled {


    override fun walkRight(walkSpeed: Float): Boolean {
        if (!canWalk()) {
            return false
        }
        speedX = walkSpeed
        return true
    }

    override fun walkLeft(walkSpeed: Float): Boolean {
        if (!canWalk()) {
            return false
        }
        speedX = - walkSpeed
        return true
    }

    override fun walkStop(): Boolean {
        if (!canWalk()) {
            return false
        }
        speedX = 0f
        return true
    }

    override fun flyDown(flySpeed: Float): Boolean {
        return if (!canFly()) {
            false
        } else {
            speedY = -flySpeed
            return true
        }
    }

    override fun flyUp(flySpeed: Float): Boolean {
        return if (!canFly()) {
            false
        } else {
            speedY = flySpeed
            return true
        }
    }


    override fun flyStop(): Boolean {
        return if (!canFly()) {
            false
        } else {
            speedY = 0f
            return true
        }
    }


    override fun jump(maxJumps: Int): Boolean {
        if (jumpsAmount >= maxJumps) {
            return false
        }
        this.jump()
        return true
    }

    override fun lookTo(l: GameRectangle) {
        this.lookTo(if (centerHorizontal < l.centerHorizontal) 1 else -1)
    }

    override fun lookTo(directionNew: Int) {
        val directionOld = direction
        if (directionNew != directionOld) {
            direction = directionNew
            this.animation.update()
        }
    }


    override fun doLogic(delta: Float) {
        controller.doLogic(delta)
        super.doLogic(delta)
    }

    override fun draw(delta: Float, batcher: Batch) {
        super.draw(delta, batcher)
        controller.drawAbove(delta, batcher)
    }

    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        val death = super.damage(value, damager, cause)
        controller.damage(value, damager, cause)
        return death
    }

    override fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        if (!controller.death(damager, cause)) {
            return false
        }
        return super.death(damager, cause)
    }



}