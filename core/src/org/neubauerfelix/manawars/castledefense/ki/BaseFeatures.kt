package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingAction
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.IDataCoreEntity
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import kotlin.math.abs

class BaseFeatures {

    companion object {

        // Note that does features can be applied to the enemy player as well

        fun gold(player: ICDPlayer): Double {
            return player.castle.storedMoney.toDouble()
        }
        // TODO add money per cycle as feature

        fun entityWorth(player: ICDPlayer, includeBuildings: Boolean): Double {
            var totalWorth = 0f
            for (entity in player.controller.analysis.entities) {
                if (entity is ILiving) {
                    val data: IDataCoreEntity =
                            if (entity is MEntityControlled) {
                                entity.data
                            } else if (includeBuildings && entity is CDEntityBuildingAction) {
                                entity.data
                            } else {
                                continue
                            }

                    totalWorth += entity.health/entity.healthMax * data.cost
                }
            }
            return totalWorth.toDouble()
        }

        fun distancePlayerToEnemyCastle(player: ICDPlayer): Double {
            return player.enemy.castle.getDistanceHor(player.controller.analysis.furthestX).toDouble()
        }

        fun healthTotal(player: ICDPlayer) : Double {
            return player.controller.analysis.totalHealth.toDouble()
        }

        fun healthCastle(player: ICDPlayer) : Double {
            return player.castle.health.toDouble()
        }

        fun countUnitTotal(player: ICDPlayer) : Double {
            return player.controller.analysis.units.map { (_, count) -> count }.sum().toDouble()
        }

        fun countUnit1(player: ICDPlayer) : Double {
            return player.controller.analysis.units[unit1(player)]?.toDouble() ?: 0.0
        }

        fun countUnit2(player: ICDPlayer) : Double {
            return player.controller.analysis.units[unit2(player)]?.toDouble() ?: 0.0
        }

        fun countUnit3(player: ICDPlayer) : Double {
            return player.controller.analysis.units[unit3(player)]?.toDouble() ?: 0.0
        }

        fun countUnit4(player: ICDPlayer) : Double {
            return player.controller.analysis.units[unit4(player)]?.toDouble() ?: 0.0
        }

        fun countUnit5(player: ICDPlayer) : Double {
            return player.controller.analysis.units[unit5(player)]?.toDouble() ?: 0.0
        }

        fun costUnit1(player: ICDPlayer): Double {
            return unit1(player).cost.toDouble()
        }

        fun costUnit2(player: ICDPlayer): Double {
            return unit2(player).cost.toDouble()
        }

        fun costUnit3(player: ICDPlayer): Double {
            return unit3(player).cost.toDouble()
        }

        fun costUnit4(player: ICDPlayer): Double {
            return unit4(player).cost.toDouble()
        }

        fun costUnit5(player: ICDPlayer): Double {
            return unit5(player).cost.toDouble()
        }

        fun effectivenessUnit1(player: ICDPlayer, preparation: CDKIFeaturePreparation) : Double {
            return preparation.playerUnitEffectiveness[unit1(player)]!!.toDouble()
        }

        fun effectivenessUnit2(player: ICDPlayer, preparation: CDKIFeaturePreparation) : Double {
            return preparation.playerUnitEffectiveness[unit2(player)]!!.toDouble()
        }

        fun effectivenessUnit3(player: ICDPlayer, preparation: CDKIFeaturePreparation) : Double {
            return preparation.playerUnitEffectiveness[unit3(player)]!!.toDouble()
        }

        fun effectivenessUnit4(player: ICDPlayer, preparation: CDKIFeaturePreparation) : Double {
            return preparation.playerUnitEffectiveness[unit4(player)]!!.toDouble()
        }

        fun effectivenessUnit5(player: ICDPlayer, preparation: CDKIFeaturePreparation) : Double {
            return preparation.playerUnitEffectiveness[unit5(player)]!!.toDouble()
        }

        fun unit1(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[0]
        }
        fun unit2(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[1]
        }
        fun unit3(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[2]
        }
        fun unit4(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[3]
        }
        fun unit5(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[4]
        }

        fun recentDamageDealt(preparation: CDKIFeaturePreparation) : Double {
            return preparation.damageDealt.toDouble()
        }

        fun recentDamageTaken(preparation: CDKIFeaturePreparation) : Double {
            return preparation.damageTaken.toDouble()
        }

        fun recentKills(preparation: CDKIFeaturePreparation) : Double {
            return preparation.killed.toDouble()
        }

        fun recentDeaths(preparation: CDKIFeaturePreparation) : Double {
            return preparation.died.toDouble()
        }



    }
}