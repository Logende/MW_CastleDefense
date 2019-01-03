package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause



open class MEntityActionUser(animationProducer: IEntityAnimationProducer, health: Float, mana: Float, override val actions: Array<IDataAction>,
                        override val manaRegen: Float): MEntityAnimated(animationProducer, health), IActionUser {

    override val manaMax: Float = mana
    override var mana: Float = mana / 2f

    init {
        //actions.sortWith({a : IDataAction, b: IDataAction -> 1})
        actions.sortWith(object: Comparator<IDataAction>{
            override fun compare(a1: IDataAction, a2: IDataAction): Int = when {
                a1.manaCost > a2.manaCost -> 1
                a1.manaCost == a2.manaCost -> 0
                else -> -1
            }
        }, 0, 4)
    }

    fun addMana(value: Float) {
        this.mana = Math.min(this.manaMax, this.mana + value)
    }

    fun takeMana(value: Float) {
        require(this.mana > value)
        this.mana -= value
    }

    fun executeAction(id: Int): Boolean {
        require(actions.size > id)

        val action = actions[id]
        if (mana < action.manaCost) {
            return false
        }
        if (!action.canUse(this)) {
            return false
        }
        if (action.action(this)) {
            takeMana(action.manaCost.toFloat())
            this.animation.playBodyEffect(action.ownerAnimationEffect, action.weaponType)
            return true
        }
        return false
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