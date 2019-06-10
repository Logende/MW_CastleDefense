package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit


class CDControllerBot : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities
    private val strategicFactors: MutableMap<IDataUnit, Float> = hashMapOf()

    private var nextUnitChooseTime: Long = 0L


    override val playerControlled: Boolean
        get() = false

    lateinit var nextUnit: IDataUnit

    override fun showControls() {
    }

    override fun hideControls() {
    }

    override fun doLogic(delta: Float) {
        analysis.update(player)
        if (nextUnitChooseTime <= MManaWars.m.screen.getGameTime()) {
            this.chooseUnit()
            nextUnitChooseTime = MManaWars.m.screen.getGameTime() + CDConstants.CASTLEDEFENSE_CASTLE_CHOOSE_UNIT_DELAY
            System.out.println("choose ${nextUnit.name}")
        }

        if (player.castle.gold >= nextUnit.analysis.cost) {
            player.castle.gold -= nextUnit.analysis.cost
            player.spawnUnit(nextUnit)
            this.chooseUnit()
        }
    }

    private fun chooseUnit(): IDataUnit {
        player.tribe.army.units.forEach { strategicFactors[it] =
                CDManaWars.cd.getArmyAnalysisHandler().getStrategicFactor(it, player) }

        val ranking = player.tribe.army.units.sortedByDescending { strategicFactors[it]!! }
        this.nextUnit = ranking.first()
        return this.nextUnit
    }
}