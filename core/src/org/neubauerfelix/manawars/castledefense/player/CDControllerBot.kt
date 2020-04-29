package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.ki.machinelearning.CDKIMachineLearning
import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.ICDKI
import org.neubauerfelix.manawars.castledefense.ki.CDKIFeatureExtractor
import org.neubauerfelix.manawars.castledefense.ki.machinelearning.CDKIModelRandom
import org.neubauerfelix.manawars.castledefense.ki.traditional.CDKITraditionalFelix
import org.neubauerfelix.manawars.castledefense.ki.traditional.CDKITraditionalNaive
import org.neubauerfelix.manawars.manawars.MManaWars


class CDControllerBot(val ki: ICDKI) : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities

    private var nextUnitChooseTime: Long = MManaWars.m.screen.getGameTime() + CDConstants.CASTLE_CHOOSE_ACTION_DELAY


    override val playerControlled: Boolean
        get() = false

    var nextAction: CDKILabel = CDKILabel.NONE


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
            this.nextAction = CDKILabel.NONE // wait a full tick before deciding next action
        }
    }

    private fun chooseAction(): CDKILabel {
        this.nextAction = ki.compute(player)
        return this.nextAction
    }


    override fun load() {
        analysis.load()
    }

    override fun dispose() {
        analysis.dispose()
    }
}