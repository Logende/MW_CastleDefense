package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.EntityAnimationAny
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyHumanAnimating
import org.neubauerfelix.manawars.manawars.entities.animation.rider.BodyRider
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause



open class MEntityActionUser(animationProducer: IEntityAnimationProducer, health: Float, mana: Float, val actions: Array<IDataAction>,
                        override val manaRegen: Float): MEntityAnimated(animationProducer, health), IActionUser {

    override val manaMax: Float = mana
    override var mana: Float = mana / 2f

    init {
        //actions.sortWith({a : IDataAction, b: IDataAction -> 1})
        if (!actions.isEmpty()) {
            actions.sortWith(object : Comparator<IDataAction> {
                override fun compare(a1: IDataAction, a2: IDataAction): Int = when {
                    a1.manaCost > a2.manaCost -> 1
                    a1.manaCost == a2.manaCost -> 0
                    else -> -1
                }
            }, 0, actions.size)
            if (MConstants.ALWAYS_EQUIP_WEAPONS && actions[0].weaponType != null) {
                if (this.animation is EntityAnimationAny) {
                    if (this.animation.body is BodyHumanAnimating) {
                        this.animation.body.setWeapon(actions[0].weaponType)

                    } else if (this.animation.body is BodyRider) {
                        if (this.animation.body.human is BodyHumanAnimating) {
                            this.animation.body.human.setWeapon(actions[0].weaponType)
                        }
                    }
                }
            }
        }
    }

    fun addMana(value: Float) {
        this.mana = Math.min(this.manaMax, this.mana + value)
    }

    fun takeMana(value: Float) {
        require(this.mana > value)
        this.mana -= value
    }

    override fun executeAction(id: Int): Boolean {
        require(actions.size > id)

        val action = actions[id]
        if (mana < action.manaCost) {
            return false
        }
        if (!this.canPerformActions()) {
            return false
        }
        if (!action.canUse(this)) {
            return false
        }
        if (action.action(this)) {
            takeMana(action.manaCost.toFloat())
            this.animation.playBodyEffect(action.animationEffect, action.weaponType)
            return true
        }
        return false
    }

    override fun canPerformActions(): Boolean {
        return !this.animation.playingBodyEffect
    }

    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        this.addMana(this.manaRegen * delta)
    }

    override fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        val died = super.death(damager, cause)
        if (died) {
            this.mana = 0f
        }
        return died
    }


}
