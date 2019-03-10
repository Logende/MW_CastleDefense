package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.entities.controller.ControllerCastleDefense
import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.ITeamable

interface ICDPlayer : ITeamable {

    val army: IDataArmy
    val controller: ICDController
    val castle: ICDEntityCastle
    val formation: ICDFormation

    val enemy: ICDPlayer


    fun spawnUnit(unit: IDataUnit) {
        val loc = castle.unitSpawnLocation
        unit.produce(loc.x - unit.animation.bodyWidth/2f, loc.y - unit.animation.bodyHeight,
                ControllerCastleDefense(this), team)
    }

    fun spawnCastle(leftSide: Boolean, mapWidth: Float)

}