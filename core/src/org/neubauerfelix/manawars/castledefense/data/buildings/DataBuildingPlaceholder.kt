package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingPlaceholder
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding

class DataBuildingPlaceholder(textureName: String, override val name: String) :
        IDataBuilding {

    override val health: Float = 1f

    override val animationProducer: IEntityAnimationProducer =
            EntityAnimationProducerBuilding(textureName, textureName,
                    null, 0f)

    override fun produce(centreHor: Float, bottom: Float, team: Int): ILiving {
        val e =  CDEntityBuildingPlaceholder(this)
        e.centerHorizontal = centreHor
        e.bottom = bottom
        e.team = team
        e.spawn()
        return e
    }

}