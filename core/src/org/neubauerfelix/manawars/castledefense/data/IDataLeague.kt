package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataCastle
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.data.IAsset

interface IDataLeague {

    val name: String
    val tribes: List<IDataTribe> // possible enemy tribes
    val castles: List<IDataCastle> // castles the tribes can choose from
    val buildings: List<IDataBuilding>
    val buildingPlaceholder: IDataBuilding
    val playground: IDataPlayground

    fun getTribe(name: String): IDataTribe?
    fun getCastle(name: String): IDataCastle?
    fun getBuilding(name: String): IDataBuilding?

    val baseGoldStart: Float
    val baseGoldPerSecond: Float
    val baseHealth: Float
}