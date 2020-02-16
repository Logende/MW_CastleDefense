package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.data.units.IDataBaseUnitStats
import org.neubauerfelix.manawars.manawars.enums.MWUnitRarity
import org.neubauerfelix.manawars.manawars.enums.MWUnitType

interface IUnitHandler: IHandler {

    fun putUnit(unit: IDataUnit)
    fun getUnit(name: String): IDataUnit?
    fun listUnits(): Iterable<IDataUnit>


}