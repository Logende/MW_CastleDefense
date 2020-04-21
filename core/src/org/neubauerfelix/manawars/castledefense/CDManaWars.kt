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
        loadHandler(KIBalancingHandler()) // make sure it is loaded before TextVisualization
        super.loadGame()
        loadHandler(BuildingListHandler())
        loadHandler(PlaygroundListHandler())
        loadHandler(TribeHandler())
        loadHandler(ArmyAnalysisHandler())
    }

    fun getArmyAnalysisHandler(): IArmyAnalysisHandler {
        return getHandler(ArmyAnalysisHandler::class.java)
    }

    fun getTribeHandler(): ITribeHandler {
        return getHandler(TribeHandler::class.java)
    }

    fun getBuildingListHandler(): IBuildingListHandler {
        return getHandler(BuildingListHandler::class.java)
    }

    fun getPlaygroundListHandler(): IPlaygroundListHandler {
        return getHandler(PlaygroundListHandler::class.java)
    }

}