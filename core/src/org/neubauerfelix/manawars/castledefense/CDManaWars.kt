package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.analysis.ArmyAnalysisHandler
import org.neubauerfelix.manawars.castledefense.analysis.IArmyAnalysisHandler
import org.neubauerfelix.manawars.manawars.MManaWars

class CDManaWars : MManaWars() {

    companion object {
        lateinit var cd: CDManaWars
    }

    init {
        CDManaWars.cd = this
    }

    override fun load() {
        super.load()
        this.loadHandler(ArmyAnalysisHandler())
    }

    fun getArmyAnalysisHandler(): IArmyAnalysisHandler {
        return getHandler(ArmyAnalysisHandler::class.java)
    }

}