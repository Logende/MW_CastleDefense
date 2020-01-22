package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction

interface IDataBuildingAction : IDataBuilding {

    val action: IDataAction
    val cooldown: Float
}