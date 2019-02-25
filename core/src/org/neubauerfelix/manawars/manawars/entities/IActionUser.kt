package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity


interface IActionUser: ILooking, ITeamable, IEntity, IAnimated {


    val actionCooldown: Float
    fun executeAction(): Boolean
    fun canPerformAction(): Boolean


}