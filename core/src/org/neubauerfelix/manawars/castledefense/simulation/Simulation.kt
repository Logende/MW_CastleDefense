package org.neubauerfelix.manawars.castledefense.simulation

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class Simulation(val player: ICDPlayer) {

 /*   val unitsPlayer: SimulationUnits
    val unitsEnemy: SimulationUnits

    init {
        val enemy = player.enemy
        val analysisPlayer = player.controller.analysis
        val analysisEnemy = enemy.controller.analysis

        unitsPlayer = SimulationUnits(analysisPlayer.entities, player.formation, player.tribe.army, player.castle.health)
        unitsEnemy = SimulationUnits(analysisEnemy.entities, enemy.formation, enemy.tribe.army, enemy.castle.health)
    }

    /**
     * TODO:
     * - Maybe include x coordinate of units in the simulation
     */

    // TODO: Bot soll 1. ueberleben versuchen und wenn er gut ueberlebt dann 2. versuchen viel schaden zu machen
    fun addPlayerUnit(data: IDataUnit) {
        unitsPlayer.addUnit(data, player.castle, player.formation)
    }

    fun simulate(timeStep: Float, totalTime: Float) {
        //println("simulation start")
        for (i in 0 until (totalTime/timeStep).toInt()) {
            //print("simulate step $i. ")
            simulateStep(timeStep)
            //unitsPlayer.debug()
        }
        //println("simulation end")
    }


    val playerScore: Float
        get() {
            val factorPlayer = this.fix(unitsPlayer.totalDefensiveStrengthPerSecond /
                    unitsEnemy.totalOffensiveStrengthPerSecond, 10f, 0f)
            val factorEnemy = this.fix(unitsEnemy.totalDefensiveStrengthPerSecond /
                    unitsPlayer.totalOffensiveStrengthPerSecond, 10f, 0f)

            return unitsPlayer.totalHealth -
                    unitsEnemy.totalHealth
        }

    private fun simulateStep(timeStep: Float) {
        // assume there will always be some successful attacks. Else in some cases the KI would ignore unit weaknesses
        // because it would think its skill do not hit anyways
        val minAttackSuccessFactor = 0.1f
        var attackSuccessFactorPlayer = fix((unitsPlayer.totalOffensiveStrengthPerSecond -
                unitsEnemy.totalDefensiveStrengthPerSecond) / unitsPlayer.totalOffensiveStrengthPerSecond ,
                1f, minAttackSuccessFactor)

        var attackSuccessFactorEnemy = fix((unitsEnemy.totalOffensiveStrengthPerSecond -
                unitsPlayer.totalDefensiveStrengthPerSecond) / unitsEnemy.totalOffensiveStrengthPerSecond ,
                1f, minAttackSuccessFactor)

        unitsEnemy.simulateDefense(timeStep, attackSuccessFactorPlayer, unitsPlayer)
        unitsPlayer.simulateDefense(timeStep, attackSuccessFactorEnemy, unitsEnemy)
    }



    private fun fix(f: Float, max: Float, min: Float = 0f): Float {
        return if (f.isNaN()) {
            0f
        } else if (f.isInfinite()) {
            max
        } else {
            Math.min(Math.max(f, min), max)
        }
    }
*/
}