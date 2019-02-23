package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity


interface IActionUser: ILooking, ITeamable, IEntity, IAnimated {


    val mana: Float
    val manaMax: Float
    val manaRegen: Float

    fun executeAction(id: Int): Boolean
    fun canPerformActions(): Boolean


}