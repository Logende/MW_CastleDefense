package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimated

open class CDEntityBuilding(data: IDataBuilding) : MEntityAnimated(data.animationProducer,
        data.health), ILiving {

    override fun knockback(power_x: Float, power_y: Float, source: IMovable): Boolean {
        return false
    }

}


