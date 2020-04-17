package org.neubauerfelix.manawars.castledefense.ki.features

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWSkillEffectivity
import kotlin.math.abs

class BaseFeatures {

    companion object {

        fun goldPlayer(player: ICDPlayer): Double {
            return player.castle.gold.toDouble()
        }

        fun goldEnemy(player: ICDPlayer): Double {
            return player.enemy.castle.gold.toDouble()
        }

        fun distancePlayerToEnemyCastle(player: ICDPlayer): Double {
            return abs(player.controller.analysis.furthestX - player.enemy.castle.centerHorizontal).toDouble()
        }

        fun distanceEnemyToPlayerCastle(player: ICDPlayer): Double {
            return abs(player.enemy.controller.analysis.furthestX - player.castle.centerHorizontal).toDouble()
        }

        fun costUnit1(player: ICDPlayer): Double {
            return player.tribe.army.units[0].cost.toDouble()
        }

        fun costUnit2(player: ICDPlayer): Double {
            return player.tribe.army.units[1].cost.toDouble()
        }

        fun costUnit3(player: ICDPlayer): Double {
            return player.tribe.army.units[2].cost.toDouble()
        }

        fun costUnit4(player: ICDPlayer): Double {
            return player.tribe.army.units[3].cost.toDouble()
        }

        fun costUnit5(player: ICDPlayer): Double {
            return player.tribe.army.units[4].cost.toDouble()
        }




    }
}