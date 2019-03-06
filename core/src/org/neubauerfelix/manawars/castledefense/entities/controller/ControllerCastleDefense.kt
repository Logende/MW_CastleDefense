package org.neubauerfelix.manawars.castledefense.entities.controller

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IAnimatedLiving
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class ControllerCastleDefense(val player: ICDPlayer) : IController {


    override lateinit var controlled: IControlled
    private var inFormation = false


    override fun doLogic(delta: Float) {
        // If not in formation: Join if possible
        if (!inFormation) {
            if (player.formation.getDistance(this.controlled) <= CDConstants.CASTLEDEFENSE_FORMATION_JOIN_DISTANCE) {
                inFormation = true
                player.formation.addEntity(controlled)
            }
        }

        // If (now) in formation: try to keep up with assigned position
        if (inFormation) {
            controlled.goalX = player.formation.getAssignedX(controlled)
        } else {
            controlled.goalX = player.formation.centerHorizontal
        }


        if (player.enemy.controller.analysis.entities.isNotEmpty()) { // If there are enemies
            val closestEnemy = player.enemy.controller.analysis.entities.first()
            val actionProperties = controlled.action.getActionProperties(controlled.entityAnimationType)
            val range = if (closestEnemy is IAnimatedLiving) {
                actionProperties.rangeMax[closestEnemy.entityAnimationType]!!
            } else {
                actionProperties.rangeMaxAvg.toInt()
            }
            if (closestEnemy.getDistanceHor(controlled) < range) { // If closest enemy is in range
                controlled.lookTo(player.castle.direction)
                controlled.executeAction() // tries executing the action
            }

        }

    }

    override fun drawAbove(delta: Float, batcher: Batch) {
    }


    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        return true
    }

    override fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        if (inFormation) {
            player.formation.removeEntity(controlled)
        }
        return true
    }
}