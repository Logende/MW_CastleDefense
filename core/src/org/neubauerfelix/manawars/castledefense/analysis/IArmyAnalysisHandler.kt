package org.neubauerfelix.manawars.castledefense.analysis

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

interface IArmyAnalysisHandler: IHandler {


    fun getStrategicFactor(unit: IDataUnit, player: ICDPlayer): Float


}