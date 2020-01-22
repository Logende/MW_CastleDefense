package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuilding
import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingAction
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.MSkill
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding

class DataBuildingActionBallista(override val health: Float, override val action: IDataAction,
                                 override val cooldown: Float) :
        IDataBuildingAction {


    override val animationProducer: IEntityAnimationProducer =
            EntityAnimationProducerBuilding("building.ballista",
                    textureNameAnimation = "building.ballista.animation", animationFrameDuration = 0.1f)

    override fun produce(centreHor: Float, bottom: Float, team: Int): ILiving {
        val e =  CDEntityBuildingAction(this)
        e.centerHorizontal = centreHor
        e.bottom = bottom
        e.team = team
        e.spawn()
        return e
    }

    // todo: make buildings configurable? Settings: texture, health, action and cooldown, animation frame duration
}