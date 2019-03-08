package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis


class CDControllerBot : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities


    override val playerControlled: Boolean
        get() = false

    override fun showControls() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideControls() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doLogic(delta: Float) {
        analysis.update(player)

        val unit = player.army.units.get((Math.random() * player.army.units.size).toInt())
        if (player.castle.gold > 100) {
            player.castle.gold = 0
            player.spawnUnit(unit)
        }
    }
}