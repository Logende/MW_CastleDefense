package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.manawars.entities.*

class CDEntityBuildingPlaceholder(val data: IDataBuilding) :
        MEntityAnimated(data.animation, data.health) {

    fun build(building: IDataBuilding, team: Int, direction: Int) {
        this.remove = true
        building.produce(this.centerHorizontal, this.bottom, team, direction = direction,
                spawnPlaceholderOnDeath = true)
    }


    override fun knockback(power_x: Float, power_y: Float): Boolean {
        return false
    }
}