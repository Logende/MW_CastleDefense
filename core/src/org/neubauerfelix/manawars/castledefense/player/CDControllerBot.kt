package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit


class CDControllerBot : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities


    override val playerControlled: Boolean
        get() = false

    var nextUnit: IDataUnit? = null

    override fun showControls() {
    }

    override fun hideControls() {
    }

    override fun doLogic(delta: Float) {
        analysis.update(player)
        var nextUnit = this.nextUnit
        if (nextUnit == null) {
            this.chooseUnit()
            nextUnit = this.nextUnit
        }

        if (player.castle.gold >= nextUnit!!.analysis.cost) {
            player.castle.gold -= nextUnit.analysis.cost
            player.spawnUnit(nextUnit)
            this.chooseUnit()
        }
    }

    private fun chooseUnit() {
        val ranking = player.army.units.sortedByDescending { CDManaWars.cd.getArmyAnalysisHandler().getStrategicFactor(it, player) }
        this.nextUnit = ranking.first()!!
    }
}