package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.armies.IDataArmy
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.data.units.IUnitAnalysis

interface IArmyHandler: IHandler {


    fun putArmy(unit: IDataArmy)
    fun getArmy(name: String): IDataArmy?
    fun listArmies(): Iterable<IDataArmy>


}