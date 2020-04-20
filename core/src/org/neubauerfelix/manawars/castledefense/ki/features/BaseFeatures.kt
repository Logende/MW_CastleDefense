package org.neubauerfelix.manawars.castledefense.ki.features

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import kotlin.math.abs

class BaseFeatures {

    companion object {

        // Note that does features can be applied to the enemy player as well

        fun gold(player: ICDPlayer): Double {
            return player.castle.gold.toDouble()
        }
        fun distancePlayerToEnemyCastle(player: ICDPlayer): Double {
            return abs(player.controller.analysis.furthestX - player.enemy.castle.centerHorizontal).toDouble()
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

        private fun unit1(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[0]
        }
        private fun unit2(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[1]
        }
        private fun unit3(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[2]
        }
        private fun unit4(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[3]
        }
        private fun unit5(player: ICDPlayer) : IDataUnit {
            return player.tribe.army.units[4]
        }



    }
}