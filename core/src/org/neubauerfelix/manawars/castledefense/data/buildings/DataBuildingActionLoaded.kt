package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingAction
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataBuildingActionLoaded(config: Configuration, name: String) :
        IDataBuildingAction {

    override val action: IDataAction = MManaWars.m.getActionHandler().
            loadAction("action_building_$name", config.getSection("action"))!!
    override val cooldown: Float = config.getFloat("cooldown")
    override val health: Float = config.getFloat("health")

    val textureNameAlive: String
    val textureNameDead: String
    val textureNameAnimation: String?
    val animationFrameDuration: Float

    init {
        val animationParts = config.getString("animation").split(":")
        textureNameAlive = animationParts[0].trim()
        textureNameDead = if (animationParts.size >= 2) {
            animationParts[1].trim()
        } else {
            textureNameAlive
        }

        textureNameAnimation = if (animationParts.size >= 3) {
            assert(animationParts.size >= 4) // because it would be stupid if animation is selected but not duration
            animationParts[2].trim()
        } else {
            null
        }

        animationFrameDuration = if (animationParts.size >= 4) {
            animationParts[3].trim().toFloat()
        } else {
            0f
        }

    }

    override val animationProducer: IEntityAnimationProducer =
            EntityAnimationProducerBuilding(textureNameAlive, textureNameDead,
                    textureNameAnimation, animationFrameDuration)

    override fun produce(centreHor: Float, bottom: Float, team: Int): ILiving {
        val e =  CDEntityBuildingAction(this)
        e.centerHorizontal = centreHor
        e.bottom = bottom
        e.team = team
        e.spawn()
        return e
    }
}