package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.ki.CDKI
import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.features.CDKIFeatureExtractor
import org.neubauerfelix.manawars.castledefense.ki.model.CDKIModelRandom
import org.neubauerfelix.manawars.manawars.MManaWars


class CDControllerBot : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities

    private var nextUnitChooseTime: Long = 0L


    override val playerControlled: Boolean
        get() = false

    lateinit var nextAction: CDKILabel
    val ki = CDKI(CDKIModelRandom(), CDKIFeatureExtractor())


    override fun showControls() {
    }

    override fun hideControls() {
    }

    override fun doLogic(delta: Float) {
        analysis.update(player)
        if (nextUnitChooseTime <= MManaWars.m.screen.getGameTime()) {
            this.chooseAction()
            nextUnitChooseTime = MManaWars.m.screen.getGameTime() + CDConstants.CASTLE_CHOOSE_ACTION_DELAY
            println("choose ${nextAction.name}")
        }

        val cost = nextAction.getCost(player)
        if (player.castle.gold >= cost) {
            player.castle.gold -= cost
            nextAction.perform(player)
            this.chooseAction()
        }
    }

    private fun chooseAction(): CDKILabel {
        this.nextAction = ki.compute(player)
        return this.nextAction
    }
}