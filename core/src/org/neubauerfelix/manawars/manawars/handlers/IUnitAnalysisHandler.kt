package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.data.units.IUnitAnalysis

interface IUnitAnalysisHandler: IHandler {


    fun analyse(unit: IDataUnit): IUnitAnalysis


    fun analyseUnits(fileName: String)
    fun loadUnitAnalyses(fileName: String)


}