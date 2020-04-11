package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildingAction
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.MEntityActionUser
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class CDEntityBuildingAction(val data: IDataBuildingAction) :
        MEntityActionUser(data.animationProducer, data.health, data.action, data.actionCooldown) {



    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        executeAction() // tries performing action. Includes cooldown, etc.
    }

    override fun knockback(power_x: Float, power_y: Float): Boolean {
        return false
    }


    override fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        val dies = super.death(damager, cause)
        if (dies) {
            CDManaWars.cd.getBuildingListHandler().buildingPlaceholder.
                    produce(this.centerHorizontal, team = MConstants.TEAM_PEACEFUL)
        }
        return dies
    }

}