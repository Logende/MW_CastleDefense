package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanAnimating
import org.neubauerfelix.manawars.manawars.entities.animation.rider.BodyRider


open class MEntityActionUser(animationProducer: IEntityAnimationProducer, health: Float, val action: IDataAction,
                             override val actionCooldown: Long): MEntityAnimated(animationProducer, health), IActionUser {

    var actionCooldownTime = 0L

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
            this.actionCooldownTime = AManaWars.m.screen.getGameTime() + this.actionCooldown
            this.animation.playBodyEffect(action.animationEffect, action.weaponType)
            return true
        }
        return false
    }

    override fun canPerformAction(): Boolean {
        if (AManaWars.m.screen.getGameTime() > actionCooldownTime) {
            return false
        }
        return !this.animation.playingBodyEffect
    }




}
