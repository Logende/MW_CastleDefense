package org.neubauerfelix.manawars.castledefense.analysis

import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled

class ArmyAnalysisHandler : IArmyAnalysisHandler {

    override fun getStrategicFactor(unit: IDataUnit, player: ICDPlayer): Float {
        // First unit should have high survival factor and should have a skill instead of other action like heal
        if (player.controller.analysis.entities.isEmpty()) {
            return unit.analysis.survivalFactor * ( if (unit.action is IDataSkill) 1f else 0.4f)
        }

        // Otherwise the unit should be effective against the existing enemies
        val factor = this.calculateFormationValue(player, unit)

        println("chance of ${unit.name}: dE $factor")
        return factor
    }



    fun calculateFormationValue(player: ICDPlayer, proposedUnit: IDataUnit): Float {
        val analysisPlayer = player.controller.analysis
        val analysisEnemy = player.controller.analysis

        val strengthPointsPlayer = (analysisPlayer.totalDefensiveStrengthPerSecond +
                proposedUnit.analysis.defensiveStrengthPerSecond) /
                (analysisPlayer.totalOffensiveStrengthPerSecond + proposedUnit.analysis.offensiveStrengthPerSecond)
        val strengthPointsEnemy = analysisEnemy.totalDefensiveStrengthPerSecond /
                analysisEnemy.totalOffensiveStrengthPerSecond

        val unitsPlayer = LinkedHashMap(analysisPlayer.units)
        if (unitsPlayer.containsKey(proposedUnit)) {
            unitsPlayer[proposedUnit] = unitsPlayer[proposedUnit]!! +1
        } else {
            unitsPlayer[proposedUnit] = 1
        }

        val defenseStrength = this.calculateDefenseFactorWeighted(player.army, unitsPlayer, analysisEnemy.units,
                strengthPointsPlayer, strengthPointsEnemy)
        val offenseStrength = 1f / this.calculateDefenseFactorWeighted(player.enemy.army, analysisEnemy.units,
                unitsPlayer , strengthPointsEnemy, strengthPointsPlayer)
        return 0.5f * defenseStrength + 0.5f * offenseStrength
    }


    /** Calculate defense strength
     * - Strength of first row of defenders against all enemy attackers
     * - Strength of all units against all enemy attackers
     * - Strength of all units against all possible enemy attackers (considering every possible enemy unit)
     * - Unit defensive points / enemy offensive points
     */
    fun calculateDefenseFactorWeighted(army: IDataArmy, units: Map<IDataUnit, Int>, enemies: Map<IDataUnit, Int>,
                                       strengthPointsPlayer: Float, strengthPointsEnemy: Float): Float {
        val strengthFirstRowVsAllAlive = if (units.isEmpty()) 1f else
            this.calculateDefenseFactor(linkedMapOf(Pair(units.keys.first(), units.values.first())),
                enemies)
        val strengthAllAliveVsAllAlive = this.calculateDefenseFactor(units, enemies)

        val enemiesAll: MutableMap<IDataUnit, Int> = hashMapOf()
        army.units.forEach {
            enemiesAll[it] = 1
        }
        val strengthAllAliveVsAll = this.calculateDefenseFactor(units, enemiesAll)

        val strengthPoints = strengthPointsPlayer / strengthPointsEnemy

        return 0.3f * strengthFirstRowVsAllAlive + 0.3f * strengthAllAliveVsAllAlive + 0.2f * strengthAllAliveVsAll +
                0.2f * strengthPoints
    }


    fun calculateDefenseFactor(units: Map<IDataUnit, Int>, enemies: Map<IDataUnit, Int>): Float {
        val combinedFactor = units.map { (unit, unitCount) ->
            val unitsDefenseFactor = enemies.map { (enemy, enemyCount) ->
                val unitDefenseFactor = 1f / Math.max(0.1f,
                        MManaWars.m.getUnitAnalysisHandler().getAttackerStrategicFactor(enemy, unit))
                Math.pow(unitDefenseFactor.toDouble(), enemyCount.toDouble())
            }.fold(1.0) { acc, fl -> acc * fl }
            Math.pow(unitsDefenseFactor, unitCount.toDouble())
        }.fold(1.0) { acc, fl -> acc * fl }
        val combinedFactorNormalized = Math.max(0.0, Math.pow(combinedFactor, 1.0 /
                (units.map { it.value }.sum() + enemies.map { it.value }.sum())))
        return combinedFactorNormalized.toFloat()
    }

}