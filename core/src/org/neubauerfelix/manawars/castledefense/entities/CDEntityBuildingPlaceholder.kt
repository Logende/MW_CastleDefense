package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildingAction
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.*

class CDEntityBuildingPlaceholder(val data: IDataBuilding) : MEntityAnimated(data.animationProducer,
        data.health) {

    fun build(building: IDataBuilding, team: Int) {
        this.remove = true
        building.produce(this.centerHorizontal, this.bottom, team)
    }


    override fun knockback(power_x: Float, power_y: Float): Boolean {
        return false
    }

}