package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.manawars.entities.*

class CDEntityBuildingPlaceholder(val data: IDataBuilding, val league: IDataLeague) :
        MEntityAnimated(data.animationProducer, data.health) {

    fun build(building: IDataBuilding, team: Int) {
        this.remove = true
        building.produce(this.centerHorizontal, this.bottom, team, league)
    }


    override fun knockback(power_x: Float, power_y: Float): Boolean {
        return false
    }
}