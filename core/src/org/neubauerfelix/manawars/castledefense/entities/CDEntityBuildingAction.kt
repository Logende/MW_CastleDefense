package org.neubauerfelix.manawars.castledefense.entities

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildingAction
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.MEntityActionUser
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class CDEntityBuildingAction(val data: IDataBuildingAction, private val spawnPlaceholderOnDeath: Boolean) :
        MEntityActionUser(data.animation, data.health, data.action, data.actionCooldown) {



    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        executeAction() // tries performing action. Includes cooldown, etc.
    }

    override fun knockback(power_x: Float, power_y: Float): Boolean {
        return false
    }


    override fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        val dies = super.death(damager, cause)
        if (dies && spawnPlaceholderOnDeath) {
            CDManaWars.cd.getBuildingListHandler().buildingPlaceholder.
                    produce(this.centerHorizontal, team = MConstants.TEAM_PEACEFUL, direction = this.direction,
                            spawnPlaceholderOnDeath = true)
        }
        return dies
    }

    override fun draw(batcher: Batch) {
        super.draw(batcher)
        MManaWars.m.getCharacterBarHandler().drawStatsBar(batcher, this)
    }

}