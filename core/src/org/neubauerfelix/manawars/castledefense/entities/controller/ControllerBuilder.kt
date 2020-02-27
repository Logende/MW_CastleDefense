package org.neubauerfelix.manawars.castledefense.entities.controller

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingPlaceholder
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IAnimatedLiving
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class ControllerBuilder(val player: ICDPlayer) : IController {


    override lateinit var controlled: IControlled


    override fun doLogic(delta: Float) {
        val entities = MManaWars.m.screen.getEntities { it is CDEntityBuildingPlaceholder }
        if (entities.isNotEmpty()) {
            val closest = entities.sortedBy { it.getDistanceHor(controlled) }.first()
            controlled.goalX = closest.centerHorizontal

            if (closest.getDistanceHor(controlled) == 0f) {
                controlled.executeAction()
            }

        }

    }

    override fun drawAbove(delta: Float, batcher: Batch) {
        MManaWars.m.getCharacterBarHandler().drawStatsBar(batcher, controlled)
    }


    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        return true
    }

    override fun death(damager: IEntity, cause: MWDamageCause) {
    }
}