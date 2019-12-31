package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.units.IDataBaseUnitStats
import org.neubauerfelix.manawars.manawars.enums.MWUnitRarity
import org.neubauerfelix.manawars.manawars.enums.MWUnitType

interface IBaseUnitHandler : IHandler {

    fun getBaseUnitStats(unitType: MWUnitType, unitRarity: MWUnitRarity): IDataBaseUnitStats
}