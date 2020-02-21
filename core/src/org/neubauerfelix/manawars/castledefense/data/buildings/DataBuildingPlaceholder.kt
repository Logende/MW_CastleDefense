package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingPlaceholder
import org.neubauerfelix.manawars.game.IComponent
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

    override val cost: Int = -1


    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(x: Float, y: Float, width: Float, height: Float, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}