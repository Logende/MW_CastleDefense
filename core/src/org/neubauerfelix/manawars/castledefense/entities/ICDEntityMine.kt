package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized

interface ICDEntityMine : ISized, IEntity, IDrawable {

    var goldPerCharge: Int
    var chargePeriod: Float
    val castle: ICDEntityCastle

}