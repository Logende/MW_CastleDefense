package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class UnitHandler : IUnitHandler {

    private val units = java.util.HashMap<String, IDataUnit>()


    override fun putUnit(unit: IDataUnit) {
        units[unit.name] = unit
    }

    override fun getUnit(name: String): IDataUnit? {
        return units[name]
    }

    override fun listUnits(): Iterable<IDataUnit> {
        return units.values
    }


}