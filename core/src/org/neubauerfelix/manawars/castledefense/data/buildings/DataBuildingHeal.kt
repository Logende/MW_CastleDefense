package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingHeal
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding

class DataBuildingHeal(override val health: Float, override val healingPower: Float, override val cooldown: Long,
                       override val range: Float) :
        IDataBuildingHeal {


    override val animationProducer: IEntityAnimationProducer =
            EntityAnimationProducerBuilding("building.heal")

    override fun produce(centreHor: Float, bottom: Float, team: Int): ILiving {
        val e =  CDEntityBuildingHeal(this)
        e.centerHorizontal = centreHor
        e.bottom = bottom
        e.team = team
        e.spawn()
        return e
    }
}