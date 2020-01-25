package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

interface IDataArmy {

    val tribe: IDataTribe
    val units: List<IDataUnit> // units sorted by formation: for example shield guys should be first and long range entities last


}