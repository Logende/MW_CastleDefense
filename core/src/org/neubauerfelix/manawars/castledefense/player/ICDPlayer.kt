package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.entities.controller.ControllerCastleDefense
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.ITeamable

interface ICDPlayer : ITeamable {

    val tribe: IDataTribe
    val controller: ICDController
    val castle: ICDEntityCastle
    val formation: ICDFormation

    val enemy: ICDPlayer


    fun spawnUnit(unit: IDataUnit): IControlled {
        val loc = castle.unitSpawnLocation
        return unit.produce(loc.x - unit.animation.bodyWidth/2f, loc.y - unit.animation.bodyHeight,
                ControllerCastleDefense(this), team)
    }

    fun spawnCastle(leftSide: Boolean, mapWidth: Float)

}