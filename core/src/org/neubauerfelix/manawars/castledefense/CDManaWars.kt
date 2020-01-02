package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.analysis.ArmyAnalysisHandler
import org.neubauerfelix.manawars.castledefense.analysis.IArmyAnalysisHandler
import org.neubauerfelix.manawars.castledefense.handlers.*
import org.neubauerfelix.manawars.manawars.MManaWars

class CDManaWars : MManaWars() {

    companion object {
        lateinit var cd: CDManaWars
    }

    init {
        cd = this
    }

    override fun loadGame() {
        super.loadGame()
        loadHandler(LeagueHandler())
        loadHandler(ArmyAnalysisHandler())
    }

    fun getArmyAnalysisHandler(): IArmyAnalysisHandler {
        return getHandler(ArmyAnalysisHandler::class.java)
    }

    fun getLeagueHandler(): ILeagueHandler {
        return getHandler(LeagueHandler::class.java)
    }

}