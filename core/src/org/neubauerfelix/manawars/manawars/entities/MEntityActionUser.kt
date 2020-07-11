package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanAnimating
import org.neubauerfelix.manawars.manawars.entities.animation.rider.BodyRider


open class MEntityActionUser(animationProducer: IEntityAnimationProducer, knockbackFactor: Float, health: Float,
                             override val action: IDataAction, override val actionCooldown: Float):
        MEntityAnimated(animationProducer, knockbackFactor, health), IActionUser {

    private var actionCooldownTime = 0L


    init {
        if (MConstants.ALWAYS_EQUIP_WEAPONS && action.weaponType != null) {
            if (this.animation is EntityAnimationAny) {
                if (this.animation.body is BodyHumanAnimating) {
                    this.animation.body.setWeapon(action.weaponType)

                } else if (this.animation.body is BodyRider) {
                    if (this.animation.body.human is BodyHumanAnimating) {
                        this.animation.body.human.setWeapon(action.weaponType)
                    }
                }
            }
        }
    }

    fun reduceCooldownTime(value: Long) {
        this.actionCooldownTime -= value
    }


    override fun executeAction(): Boolean {
        if (!this.canPerformAction()) {
            return false
        }
        if (!action.canUse(this)) {
            return false
        }
        if (action.action(this)) {
            this.actionCooldownTime = AManaWars.m.screen.getGameTime() + (this.actionCooldown * 1000).toLong()
            this.animation.playBodyEffect(action.animationEffect)
            // todo: play sound
            return true
        }
        return false
    }

    override fun canPerformAction(): Boolean {
        if (AManaWars.m.screen.getGameTime() < actionCooldownTime) {
            return false
        }
        return !this.animation.playingBodyEffect
    }

    override fun updateTarget(possibleTargets: Iterable<IEntity>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
