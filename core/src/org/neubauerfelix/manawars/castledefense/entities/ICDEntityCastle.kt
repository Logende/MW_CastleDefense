package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.ILocated

interface ICDEntityCastle : ICDEntityBuilding{

    val unitSpawnLocation: ILocated
    var storedMoney: Int // for special actions
    var moneyPerCycle: Int // for units
    val player: ICDPlayer

}