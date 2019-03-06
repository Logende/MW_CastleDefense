package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction


interface IActionUser: ILooking, ITeamable, IEntity, IAnimated {

    val action: IDataAction
    val actionCooldown: Float
    fun executeAction(): Boolean
    fun canPerformAction(): Boolean

    fun updateTarget(possibleTargets: Iterable<IEntity>)


}