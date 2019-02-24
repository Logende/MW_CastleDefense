package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity


interface IActionUser: ILooking, ITeamable, IEntity, IAnimated {


    val actionCooldown: Long
    fun executeAction(): Boolean
    fun canPerformAction(): Boolean


}