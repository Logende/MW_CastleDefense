package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.ICDKI
import org.neubauerfelix.manawars.manawars.MManaWars


class CDControllerBot(val ki: ICDKI) : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities

    private var nextActionChooseTime: Long = 0L + CDConstants.CASTLE_CHOOSE_ACTION_DELAY


    override val playerControlled: Boolean
        get() = false

    var nextAction: CDKILabel = CDKILabel.NONE


    override fun showControls() {
    }

    override fun hideControls() {
    }

    override fun doLogic(delta: Float) {
        analysis.update(player)
        val time = MManaWars.m.screen.getGameTime()
        if (nextActionChooseTime <= time) {
            this.chooseAction()
            nextActionChooseTime = MManaWars.m.screen.getGameTime() + CDConstants.CASTLE_CHOOSE_ACTION_DELAY
        }

        val cost = nextAction.getCost(player)
        /*if (player.castle.storedMoney >= cost) {
            player.castle.storedMoney -= cost
            nextAction.perform(player)
            this.nextAction = CDKILabel.NONE // wait a full tick before deciding next action
        }*/ // TODO: also care about mana actions and not just units

        player.clearUnitsToBuild()
        val unitsToBuild = ki.getUnitsToBuildNextCycle(player)
        for (unit in unitsToBuild) {
            player.orderUnitToBuild(unit)
        }
        // todo: do not execute this often

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