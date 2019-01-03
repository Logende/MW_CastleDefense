package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.enums.MWState


interface IStateable: ILiving {

    val stateTrigger: IEntity?
    val state: MWState?

    fun setState(state: MWState, duration: Float, trigger: IEntity)
    fun resetState()
    fun getStateEffectivity(state: MWState): MWStateEffectivity

}