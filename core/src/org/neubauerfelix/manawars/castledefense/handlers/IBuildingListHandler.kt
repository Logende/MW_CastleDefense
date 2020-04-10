package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.castledefense.data.buildings.DataBuildingPlaceholder
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.game.IHandler

interface IBuildingListHandler: IHandler {


    val buildings: Map<String, IDataBuilding>
    val buildingPlaceholder: DataBuildingPlaceholder


}