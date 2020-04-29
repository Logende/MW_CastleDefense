package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.events.EntityDamageEvent
import org.neubauerfelix.manawars.manawars.events.EntityDeathEvent

class CDKIFeaturePreparation {


    val enemyUnitDistribution = LinkedHashMap<IDataUnit, Float>()
    val playerUnitEffectiveness = LinkedHashMap<IDataUnit, Float>()

    var damageTaken = 0f
    var damageDealt = 0f
    var died = 0
    var killed = 0
    var castleTookDamage = false

    fun prepare(player: ICDPlayer) {
        enemyUnitDistribution.clear()
        enemyUnitDistribution.putAll(calculateUnitDistribution(player.enemy))
        playerUnitEffectiveness.clear()
        playerUnitEffectiveness.putAll(this.calculateUnitsEffectiveness(player, enemyUnitDistribution))
    }

    private fun calculateCombatStats(player: ICDPlayer) {
        castleTookDamage = false
        val a = player.controller.analysis
        for (event in a.recentEvents) {
            if (event is EntityDamageEvent &&! event.cancelled) {
                val entity = event.entity
                if (entity is IControlled) {
                    if (entity.team == player.team) {
                        damageTaken+= event.damage
                    }else if (entity.team == player.enemy.team) {
                        damageDealt+= event.damage
                    }
                }
                if (entity is ICDEntityCastle) {
                    castleTookDamage = true
                }
            }
            if (event is EntityDeathEvent &&! event.cancelled) {
                val entity = event.entity
                if (entity is IControlled) {
                    if (entity.team == player.team) {
                        died++
                    }else if (entity.team == player.enemy.team) {
                        killed++
                    }
                }
            }
        }
    }

    private fun calculateUnitsEffectiveness(player: ICDPlayer, enemyUnitDistribution: Map<IDataUnit, Float>) :
            Map<IDataUnit, Float> {
        val unitEffectivenessAbsolute = player.tribe.army.units.associateWith {
            this.calculateUnitEffectiveness(it, enemyUnitDistribution)
        }
        val effectivenessTotal = unitEffectivenessAbsolute.values.sum()
        return if (effectivenessTotal == 0f) {
            unitEffectivenessAbsolute
        } else {
            val result = LinkedHashMap<IDataUnit, Float>()
            for ((unit, absoluteValue) in unitEffectivenessAbsolute) {
                result[unit] = absoluteValue / effectivenessTotal
            }
            result
        }
    }

    private fun calculateUnitEffectiveness(unit: IDataUnit, enemyUnitDistribution: Map<IDataUnit, Float>) : Float {
        return enemyUnitDistribution.map { (enemy, factor) ->
            this.calculateUnitEffectiveness(unit, enemy) * factor
        }.sum()
    }

    private fun calculateUnitEffectiveness(unit: IDataUnit, enemy: IDataUnit) : Float {
        val attackFactor = calculateDamageFactor(unit, enemy)
        val defenseFactor = calculateDamageFactor(enemy, unit)
        return if(attackFactor == 0f) {
            0f
        } else if (defenseFactor == 0f) {
            2f
        } else if (defenseFactor == 1f && attackFactor == 1f) {
            1f
        } else {
            throw NotImplementedError()
        }
    }

    private fun calculateDamageFactor(attacker: IDataUnit, victim: IDataUnit) : Float {
        val action = attacker.action
        return if (action is IDataSkill) {
            victim.armor.getSkillEffectivity(action.skillClass).damageFactor
        } else {
            1f
        }
    }

    private fun calculateUnitDistribution(player: ICDPlayer) : Map<IDataUnit, Float> {
        val unitDistr = HashMap<IDataUnit, Float>()

        val a = player.controller.analysis
        var unitsWorth = 0f
        var unitCount = 0
        for (entity in a.entities) {
            if (entity is IControlled) {
                unitDistr[entity.data] = 1f + if (unitDistr.containsKey(entity.data)) {
                    unitDistr[entity.data]!!
                } else 0f

                val unitHealthPercentage = entity.health / entity.healthMax
                unitsWorth += entity.data.cost * unitHealthPercentage
                unitCount++
            }
        }
        val goldWorth = player.castle.gold
        val totalWorth = goldWorth + unitsWorth
        val unitsWorthPercentage = unitsWorth / totalWorth
        val goldWorthPercentage = goldWorth / totalWorth

        for ((unitType, count) in unitDistr) {
            unitDistr[unitType] = (count / unitCount) * unitsWorthPercentage
        }

        val unitTypes = player.tribe.army.units
        val unitTypeCount = unitTypes.count()
        for (unitType in unitTypes) {
            val current = if (unitDistr.containsKey(unitType)) {
                unitDistr[unitType]!!
            } else {
                0f
            }
            // heuristic which predicts future unit distribution; using equal weights for every unit type
            unitDistr[unitType] = current + (1f / unitTypeCount) * goldWorthPercentage
        }


        return unitDistr
    }
}