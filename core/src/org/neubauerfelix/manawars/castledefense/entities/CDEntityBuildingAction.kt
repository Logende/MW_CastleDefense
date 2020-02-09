package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildingAction
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.ITeamable
import org.neubauerfelix.manawars.manawars.entities.MEntityActionUser

class CDEntityBuildingAction(val data: IDataBuildingAction) : MEntityActionUser(data.animationProducer,
        data.health, data.action, data.cooldown) {



    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        executeAction() // tries performing action. Includes cooldown, etc.
    }

    override fun knockback(power_x: Float, power_y: Float): Boolean {
        return false
    }

}