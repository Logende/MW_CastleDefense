package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerTest

interface ICDPlayer {

    val army: IDataArmy
    val controller: ICDController
    val castle: ICDEntityCastle
    val team: Int

    val enemy: ICDPlayer


    fun spawnUnit(unit: IDataUnit) {
        val loc = castle.unitSpawnLocation
        unit.produce(loc.x, loc.y, ControllerTest(), team) // TODO: other controller
    }

    fun spawnCastle(leftSide: Boolean, mapWidth: Float)

}