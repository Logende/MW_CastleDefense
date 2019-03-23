package org.neubauerfelix.manawars.castledefense.analysis

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
        val damageEnemiesFactor = getDamageEnemiesFactor(unit, player)
        val supportAlliesFactor = getSupportAlliesFactor(unit, player)

        println("chance of ${unit.name}: dE $damageEnemiesFactor sA $supportAlliesFactor")

        return damageEnemiesFactor * supportAlliesFactor
    }

    fun getSupportAlliesFactor(unit: IDataUnit, player: ICDPlayer): Float {
        var factor = 1f
        val entities = player.controller.analysis.entities.filterIsInstance(IControlled::class.java)
        if (!entities.isEmpty()) {
            val entitySurvivalFactors = entities.map { it.data.analysis.survivalFactor * it.health / it.data.health }
            if (entitySurvivalFactors.max()!! < unit.analysis.survivalFactor) {
                factor *= 1.5f // increase chance if entity has higher survival factor than alive entities
            }
        }
        // TODO: If formation needs help quickly (big distance from own castle defensivePoints !>> enemy.offensivePoints): prefer fast units
        return factor
    }

    fun getDamageEnemiesFactor(unit: IDataUnit, player: ICDPlayer): Float {
        val factorEnemyFirst = MManaWars.m.getUnitAnalysisHandler().getAttackerStrategicFactor(unit, player.enemy.army.units.first())

        val entities = player.enemy.controller.analysis.entities.filterIsInstance(IControlled::class.java)
        if (entities.isEmpty()) {
            return factorEnemyFirst
        }

        // if there are units in own team, which can damage first enemy: Then consider all enemies. otherwise only consider first one

        val factorEnemiesAlive =entities.map {
            val strategicFactor = MManaWars.m.getUnitAnalysisHandler().getAttackerStrategicFactor(unit, it.data)
            strategicFactor
        }.reduce { acc, fl ->  acc * fl}

        return (factorEnemiesAlive * 4f + factorEnemyFirst) / 5f
    }

}