package org.neubauerfelix.manawars.castledefense.analysis

import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.castledefense.simulation.Simulation
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class ArmyAnalysisHandler : IArmyAnalysisHandler {

    override fun getStrategicFactor(unit: IDataUnit, player: ICDPlayer): Float {
        // First unit should have high survival factor and should have a skill instead of other action like heal
        if (player.controller.analysis.entities.isEmpty()) {
            println("start chance of ${unit.name}: dE ${unit.analysis.survivalFactor * ( if (unit.action is IDataSkill) 1f else 0.4f)}")
            return unit.analysis.survivalFactor * ( if (unit.action is IDataSkill) 1f else 0.4f)
        }

        // Otherwise the unit should be effective against the existing enemies
        //val factor = this.calculateFormationValue(tribe, unit)
        val factor = this.estimateArmyScore(player, unit, 20f)

        println("chance of ${unit.name}: dE $factor")
        return factor
    }



    fun calculateFormationValue(player: ICDPlayer, proposedUnit: IDataUnit): Float {
        val analysisPlayer = player.controller.analysis
        val analysisEnemy = player.enemy.controller.analysis

        var defenseRatioPlayer = fix((analysisPlayer.totalDefensiveStrengthPerSecond +
                proposedUnit.analysis.defensiveStrengthPerSecond) /
                analysisEnemy.totalOffensiveStrengthPerSecond, 5f)
        var defenseRatioEnemy = fix(analysisEnemy.totalDefensiveStrengthPerSecond /
                (analysisPlayer.totalOffensiveStrengthPerSecond + proposedUnit.analysis.offensiveStrengthPerSecond), 5f)

        //println("def ratio tribe $defenseRatioPlayer enemy $defenseRatioEnemy")


        val unitsPlayer = LinkedHashMap(analysisPlayer.units)
        if (unitsPlayer.containsKey(proposedUnit)) {
            unitsPlayer[proposedUnit] = unitsPlayer[proposedUnit]!! +1
        } else {
            unitsPlayer[proposedUnit] = 1
        }

        val defenseStrength = fix(this.calculateDefenseFactorWeighted(player.tribe.army, unitsPlayer, analysisEnemy.units,
                defenseRatioPlayer), 1000f)
        var offenseStrength = fix(1f / this.calculateDefenseFactorWeighted(player.enemy.tribe.army, analysisEnemy.units,
                unitsPlayer , defenseRatioEnemy), 1000f)

        val costFactor = 1f / Math.pow(proposedUnit.cost.toDouble(), 0.1).toFloat()
        return (0.5f * defenseStrength + 0.5f * offenseStrength)// * costFactor
    }


    /** Calculate defense strength
     * - Strength of first row of defenders against all enemy attackers
     * - Strength of all units against all enemy attackers
     * - Strength of all units against all possible enemy attackers (considering every possible enemy unit)
     * - Unit defensive points / enemy offensive points
     */
    fun calculateDefenseFactorWeighted(army: IDataArmy, units: Map<IDataUnit, Int>, enemies: Map<IDataUnit, Int>,
                                       defenseStrengthRatio: Float): Float {
        val strengthFirstRowVsAllAlive = if (units.isEmpty()) 1f else
            this.calculateDefenseFactor(linkedMapOf(Pair(units.keys.first(), units.values.first())),
                enemies)
        val strengthAllAliveVsAllAlive = this.calculateDefenseFactor(units, enemies)

        val enemiesAll: MutableMap<IDataUnit, Int> = hashMapOf()
        army.units.forEach {
            enemiesAll[it] = 1
        }
        val strengthAllAliveVsAll = this.calculateDefenseFactor(units, enemiesAll)


        val healthTotalPlayer = units.map { (unit, count) ->
            unit.analysis.survivalFactor * unit.health * count
        }.sum()

        val healthTotalEnemy = enemies.map { (unit, count) ->
            unit.analysis.survivalFactor * unit.health * count
        }.sum()

        val healthRatio = fix(healthTotalPlayer / healthTotalEnemy, 5f)

        val attackValue = units.map { (unit, count) ->
            unit.analysis.actionValue
        }.sum()

        val strengthFactor = 0.6f * strengthFirstRowVsAllAlive + 0.3f * strengthAllAliveVsAllAlive +
                0.1f * strengthAllAliveVsAll

        val defensiveFactor = 0.5f * defenseStrengthRatio + 0.5f * healthRatio
        //System.out.println("strength factor $strengthFactor defFactor $defensiveFactor attackValue $attackValue strengthRatio $defenseStrengthRatio healthRatio $healthRatio")

        return 0.8f * strengthFactor  + 0.2f * defensiveFactor * Math.min(5f, attackValue)

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


    fun calculateDefenseFactor(units: Map<IDataUnit, Int>, enemies: Map<IDataUnit, Int>): Float {
        val combinedFactor = units.map { (unit, unitCount) ->
            val unitsDefenseFactor = enemies.map { (enemy, enemyCount) ->
                val unitDefenseFactor = 1f / Math.max(0.01f,
                        MManaWars.m.getUnitAnalysisHandler().getAttackerStrategicFactor(enemy, unit))
                Math.pow(unitDefenseFactor.toDouble(), enemyCount.toDouble())
            }.fold(1.0) { acc, fl -> acc * fl }
            Math.pow(unitsDefenseFactor, unitCount.toDouble())
        }.fold(1.0) { acc, fl -> acc * fl }
        val combinedFactorNormalized = Math.max(0.0, Math.pow(combinedFactor, 1.0 /
                (units.map { it.value }.sum() + enemies.map { it.value }.sum())))
        return combinedFactorNormalized.toFloat()
    }


    fun estimateArmyScore(player: ICDPlayer, proposedUnit: IDataUnit, time: Float): Float {
        val simulation = Simulation(player)
        simulation.addPlayerUnit(proposedUnit)
        simulation.simulate(0.2f, time)
        return simulation.playerScore
    }


}