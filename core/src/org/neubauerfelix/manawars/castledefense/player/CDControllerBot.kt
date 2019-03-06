package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis


class CDControllerBot : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities
    var counter = 0f


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

        player.castle.gold += 1
        val unit = player.army.units.first()
        if (player.castle.gold > 100 && counter < 30) {
            counter ++
            player.castle.gold = 0
            player.spawnUnit(unit)
        }
    }
}