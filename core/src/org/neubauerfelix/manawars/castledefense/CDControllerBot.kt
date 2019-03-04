package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerTest

class CDControllerBot(val team: Int) : ICDController {

    lateinit var player: ICDPlayer


    override val playerControlled: Boolean
        get() = false

    override fun showControls() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideControls() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doLogic(delta: Float) {
        player.gold += 1
        val unit = player.army.units.first()
        if (player.gold > 100) {
            player.gold = 0
            unit.produce(0f, GameConstants.CONTROLPANEL_HEIGHT, ControllerTest(), team)
        }
    }
}