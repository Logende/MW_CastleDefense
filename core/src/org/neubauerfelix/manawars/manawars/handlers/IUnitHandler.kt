package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.data.units.IUnitAnalysis

interface IUnitHandler: IHandler {


    fun analyse(unit: IDataUnit): IUnitAnalysis


    fun analyseUnits(fileName: String)
    fun loadUnitAnalysis(data: IDataUnit): IUnitAnalysis


    fun putUnit(unit: IDataUnit)
    fun getUnit(name: String): IDataUnit?
    fun listUnits(): Iterable<IDataUnit>


}