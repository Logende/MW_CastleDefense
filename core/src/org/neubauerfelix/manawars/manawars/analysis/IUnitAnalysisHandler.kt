package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

interface IUnitAnalysisHandler: IHandler {


    fun analyse(unit: IDataUnit, league: IDataLeague): IUnitAnalysis

    fun getAttackerStrategicFactor(attacker: IDataUnit, target: IDataUnit): Float

    fun analyseUnits(fileName: String)
    fun loadUnitAnalysis(data: IDataUnit): IUnitAnalysis


}