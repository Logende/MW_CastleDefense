package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.handlers.*
import org.neubauerfelix.manawars.castledefense.menu.CDMainMenu
import org.neubauerfelix.manawars.manawars.MManaWars

class CDManaWars : MManaWars() {

    companion object {
        lateinit var cd: CDManaWars
    }

    init {
        cd = this
    }

    override fun loadGame() {
        loadHandler(KIBalancingHandler(), KIBalancingHandler::class.java) // make sure it is loaded before TextVisualization
        super.loadGame()
        loadHandler(BuildingListHandler(), IBuildingListHandler::class.java)
        loadHandler(PlaygroundListHandler(), IPlaygroundListHandler::class.java)
        loadHandler(TribeHandler(), ITribeHandler::class.java)
    }

    override fun loadedGame() {
        super.loadedGame()

        //startScreen(CDScreen(this), true)
         startScreen(CDMainMenu(this), true)
    }

    fun getTribeHandler(): ITribeHandler {
        return getHandler(ITribeHandler::class.java)
    }

    fun getBuildingListHandler(): IBuildingListHandler {
        return getHandler(IBuildingListHandler::class.java)
    }

    fun getPlaygroundListHandler(): IPlaygroundListHandler {
        return getHandler(IPlaygroundListHandler::class.java)
    }

}