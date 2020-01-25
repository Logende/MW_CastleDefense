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

    fun getTribe(name: String): IDataTribe?
    fun getCastle(name: String): IDataCastle?
    fun getBuilding(name: String): IDataBuilding?

    val startGoldAvg: Float
    val goldPerSecondAvg: Float
    val castleHealthAvg: Float
}