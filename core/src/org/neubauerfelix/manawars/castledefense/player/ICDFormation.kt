package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.ITeamable

interface ICDFormation : IMovable, ILooking, ITeamable {


    fun addEntity(e: IControlled)
    fun removeEntity(e: IControlled)
    fun getAssignedX(e: IControlled): Float



}