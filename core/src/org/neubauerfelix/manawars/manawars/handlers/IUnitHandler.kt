package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.analysis.IUnitAnalysis

interface IUnitHandler: IHandler {

    fun putUnit(unit: IDataUnit)
    fun getUnit(name: String): IDataUnit?
    fun listUnits(): Iterable<IDataUnit>


}