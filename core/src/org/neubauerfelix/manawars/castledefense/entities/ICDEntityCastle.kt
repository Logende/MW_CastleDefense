package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.ITeamable

interface ICDEntityCastle : ISized, IEntity, IDrawable, ICollidable, ILooking, ITeamable {

    val unitSpawnLocation: ILocated
    var gold: Int
    var goldPerCharge: Int

}