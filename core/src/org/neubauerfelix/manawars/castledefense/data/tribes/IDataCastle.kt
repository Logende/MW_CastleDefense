package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildings
import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.game.entities.ILocated

interface IDataCastle {

    val name: String
    val textureNameAlive: String
    val textureNameDead: String
    val unitSpawnOffset: ILocated
    val goldStart: Int
    val goldPerCharge: Int
    val health: Float


    val buildings: IDataBuildings



}