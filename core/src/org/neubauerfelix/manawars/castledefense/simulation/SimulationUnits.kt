package org.neubauerfelix.manawars.castledefense.simulation

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.castledefense.player.ICDFormation
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled

class SimulationUnits(entities: Iterable<IEntity>, formation: ICDFormation, val army: IDataArmy,
                      castleHealth: Float) {

    val units: MutableList<SimulationUnit> = mutableListOf()
    val castle: SimulationCastle = SimulationCastle(castleHealth)

    init {
        entities.forEach {
            if (it is IControlled) {
                val walkToFormationTime = if (formation.isContained(it)) {
                    0f
                } else {
                    it.getDistanceHor(formation.centerHorizontal) / it.walkSpeedMax + // Dauer um mit v_max die Formation zu erreichen
                            (it.walkSpeedMax - Math.abs(it.speedX)) / it.walkAcceleration // Dauer um zu beschleunigen
                }
                units.add(SimulationUnit(it.data, it.health, walkToFormationTime))
            }
        }
    }

    fun addUnit(unit: SimulationUnit) {
        units.add(unit)
    }
    fun addUnit(data: IDataUnit, castle: ICDEntityCastle, formation: ICDFormation) {
        val walkToFormationTime = Math.max(0f, castle.unitSpawnLocation.getDistanceHor(formation.centerHorizontal) / data.walkSpeedMax +
                data.walkSpeedMax / data.walkAcceleration)

        val saveMoneyTime = Math.max(0f, (data.cost - castle.gold) * castle.goldPerCharge /
                CDConstants.CASTLEDEFENSE_CASTLE_GOLD_CHARGE_DELAY)

        this.addUnit(SimulationUnit(data, data.health, walkToFormationTime + saveMoneyTime))
    }




    val totalHealth: Float
        get() {
            return units.filter { it.walkToFormationTime == 0f }.
                    map { it.health * it.data.analysis.survivalFactor }.sum()
        }

    val totalOffensiveStrengthPerSecond: Float
        get() {
            return units.filter { it.walkToFormationTime == 0f }.
                    map { it.data.analysis.offensiveStrengthPerSecond }.sum()
        }
    val totalDefensiveStrengthPerSecond: Float
        get() {
            return units.filter { it.walkToFormationTime == 0f }.
                    map { it.data.analysis.defensiveStrengthPerSecond }.sum()
        }



    // Note: right now the simulation requires small time steps because only one unit per step can be attacked
    // if the unit should die by first attack, other attacks are ignored
    fun simulateDefense(time: Float, attackSuccessFactorEnemy: Float, unitsEnemy: SimulationUnits) {
        // Update position of units which are not in formation yet
        units.filter { it.walkToFormationTime > 0f }.forEach {
            it.walkToFormationTime = Math.max(0f, it.walkToFormationTime - time)
        }

        val unitsInFormation = units.filter { it.walkToFormationTime == 0f }
        if (unitsInFormation.isEmpty()) {
            castle.health -= unitsEnemy.totalOffensiveStrengthPerSecond * time
            return
        }

        // Pick first defender (unit in formation with unit position front and lowest health)
        val firstDefender = unitsInFormation.sortedBy {
            army.units.indexOf(it.data) * 50000 + it.health
        }.first()

        // Damage first defender depending on ally and enemy strength
        val damage = unitsEnemy.units.filter { it.walkToFormationTime == 0f }.map {
            val factor = MManaWars.m.getUnitAnalysisHandler().getAttackerStrategicFactor(it.data, firstDefender.data)
            factor * it.data.analysis.actionValue
        }.sum() * attackSuccessFactorEnemy * time
        firstDefender.health -= damage

        if (firstDefender.health <= 0) {
            units.remove(firstDefender)
        }


    }

    fun debug() {
        print("units:")
        units.forEach {
            print(" ${it.data.name} (${it.health}, ${it.walkToFormationTime})")
        }
        println(". $totalHealth")
    }

}