package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.enums.*

interface IDataActionUser {

    val unitType: MWUnitType
    val action: IDataAction
    var actionCooldown: Float

}