package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.ITeamable

interface ICDEntityBuilding :  ISized, IEntity, IDrawable, ICollidable, ILooking, ITeamable, ILiving {
}