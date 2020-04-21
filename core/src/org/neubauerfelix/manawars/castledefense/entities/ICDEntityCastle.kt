package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.ILocated

interface ICDEntityCastle : ICDEntityBuilding{

    val unitSpawnLocation: ILocated
    var gold: Int
    var goldPerCharge: Int
    val player: ICDPlayer

}