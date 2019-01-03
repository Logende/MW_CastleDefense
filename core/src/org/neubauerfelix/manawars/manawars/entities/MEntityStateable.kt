package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause
import org.neubauerfelix.manawars.manawars.enums.MWState
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.manawars.enums.MWStateEffectivity


class MEntityStateable(animationProducer: IEntityAnimationProducer, health: Float, mana: Float, actions: Array<IDataAction>,
                       manaRegen: Float, val stateMultipliers: Map<MWState, MWStateEffectivity>):
        MEntityActionUser(animationProducer, health, mana, actions, manaRegen), IStateable {


    companion object {
        const val STATE_ACTION_DELAY = 1f
    }



    override var state: MWState? = null
        private set

    override var stateTrigger: IEntity? = null
        private set

    private var stateTimeLeft: Float = 0f
    private var stateActionTimeLeft: Float = 0f

    /**
     * Updates the state of the entity with a certain state effect.
     * If the entity currently has a state with a higher priority ([MWState.getWorth]) nothing will change.
     * @param state State to set.
     * @param duration Duration of the state in seconds.
     * @param stateTrigger Entity which is responsible for the state effect.
     */
    override fun setState(state: MWState, duration: Float, stateTrigger: IEntity) {
        if (state === this.state && state === MWState.FROZEN) { //FROZEN state can not be extended: Needs to time out before it can be set again
            return
        }
        if (getStateEffectivity(state) === MWStateEffectivity.IMMUNE) {
            return
        }
        if (this.state != null) {
            if (this.state!!.getWorth(this) > state.getWorth(this)) {
                return
            }
            resetState()
        }
        this.stateTimeLeft = duration
        this.stateTrigger = stateTrigger
        this.state = state
        this.stateActionTimeLeft = STATE_ACTION_DELAY
        state.start(this)
    }


    /**
     * Resets the state of the entity by stopping the currently active state effect (if existing).
     */
    override fun resetState() {
        if (state != null) {
            state!!.end(this)
            state = null
            stateActionTimeLeft = 0f
            stateTimeLeft = 0f
            stateTrigger = null
        }
    }


    override fun getStateEffectivity(state: MWState): MWStateEffectivity {
        return if (stateMultipliers.containsKey(state)) {
            stateMultipliers.get(state)!!
        } else {
            MWStateEffectivity.NORMAL
        }
    }


    override fun draw(delta: Float, batcher: Batch) {
        super.draw(delta, batcher)
        if (state != null) {
            batcher.draw(state!!.animation!!.getKeyFrame(this.stateTimeLeft), x, y, width, height)
        }
    }

    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        var value = value
        if (state === MWState.SLAGGED) {
            value *= if (getStateEffectivity(MWState.SLAGGED) === MWStateEffectivity.EFFECTIVE) 2f else 1.5f
        }
        return super.damage(value, damager, cause)
    }


    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        if (state != null) {
            stateActionTimeLeft -= delta
            if (stateActionTimeLeft <= 0) {
                state!!.effect(this, getStateEffectivity(this.state!!) === MWStateEffectivity.EFFECTIVE)
                stateActionTimeLeft = STATE_ACTION_DELAY
            }

            stateTimeLeft -= delta
            if (stateTimeLeft <= 0) {
                resetState()
            }
        }
    }

    override fun knockback(powerX: Float, powerY: Float): Boolean {
        if (state !== MWState.FROZEN) { //TODO: Maybe allow knockback on frozen entities
            return super.knockback(powerX, powerY)
        }
        return false
    }

}
