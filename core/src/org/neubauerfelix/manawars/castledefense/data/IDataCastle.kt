package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.manawars.data.units.DataUnitUpgrade
import org.neubauerfelix.manawars.manawars.enums.MWUpgrade

interface IDataCastle : IAsset {

    val name: String
    val textureName: String
    val unitSpawnOffset: ILocated
    val goldStart: Int
    val goldPerCharge: Int
    val health: Float


    val buildings: IDataBuildings



}