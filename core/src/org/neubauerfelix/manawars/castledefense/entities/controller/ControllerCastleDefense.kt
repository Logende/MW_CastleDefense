package org.neubauerfelix.manawars.castledefense.entities.controller

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.MEntityDestructiveWave
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause
import kotlin.math.abs
import kotlin.math.min

class ControllerCastleDefense(val player: ICDPlayer) : IController {


    override lateinit var controlled: IControlled


    override fun doLogic(delta: Float) {
        // If not in formation: Join if possible
        if (!player.formation.isContained(controlled)) {
            if (player.formation.getDistance(this.controlled) <= CDConstants.FORMATION_JOIN_DISTANCE) {
                player.formation.addEntity(controlled)
            }
        }

        // If (now) in formation: try to keep up with assigned position
        if (player.formation.isContained(controlled)) {
            controlled.walkSpeedMax = min(controlled.data.walkSpeedMax, player.formation.unitMoveSpeedLimit)
            controlled.goalX = player.formation.getAssignedX(controlled)+ player.castle.direction * 15f
        } else {
            controlled.walkSpeedMax = controlled.data.walkSpeedMax
            controlled.goalX = player.formation.centerHorizontal
        }


        if (player.enemy.controller.analysis.entities.isNotEmpty()) { // If there are enemies
            val closestEnemy = player.enemy.controller.analysis.entities.first()
            val range = controlled.action.rangeMax
            if (controlled.speedX == 0f && controlled.direction != player.castle.direction) {
                controlled.lookTo(player.castle.direction)
            }
            if (closestEnemy.getDistanceHor(controlled) < range && controlled.direction == player.castle.direction) { // If closest enemy is in range
                controlled.executeAction() // tries executing the action
            }

        }

        if (abs(controlled.speedX) > MConstants.UNIT_AVG_WALK_SPEED_MAX && controlled.speedY == 0f) {
            val ownsAura = MManaWars.m.screen.getEntities { it is MEntityDestructiveWave && it.owner == controlled }.isNotEmpty()
            if (!ownsAura) {
                val aura = MEntityDestructiveWave(controlled.centerHorizontal, controlled.centerVertical,
                        controlled.width * 1.2f, controlled.height * 1.2f, 200,
                        controlled.width * 1.8f, controlled.height * 1.8f, controlled)
                aura.spawn()
            }
        }

    }

    override fun drawAbove(batcher: Batch) {
        MManaWars.m.getCharacterBarHandler().drawStatsBar(batcher, controlled)
    }


    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        return true
    }

    override fun death(damager: IEntity, cause: MWDamageCause) {
        if (player.formation.isContained(controlled)) {
            player.formation.removeEntity(controlled)
        }
    }
}