package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction


interface IActionUser: ILooking, ITeamable {


    val mana: Float
    val manaMax: Float
    val manaRegen: Float
    val actions: Array<IDataAction>

}