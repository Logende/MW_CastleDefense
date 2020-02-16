package org.neubauerfelix.manawars.castledefense.data.actions

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction


interface IDataActionBuild : IDataAction {


    val building: IDataBuilding



}
