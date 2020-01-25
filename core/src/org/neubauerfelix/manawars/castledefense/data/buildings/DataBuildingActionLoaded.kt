package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingAction
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataBuildingActionLoaded(config: Configuration, override val name: String) :
        IDataBuildingAction {

    override val action: IDataAction = MManaWars.m.getActionHandler().
            loadAction("action_building_$name", config.getSection("action"))!!
    override val cooldown: Float = config.getFloat("cooldown")
    override val health: Float = config.getFloat("health")

    val textureNameAlive: String = config.getString("texture_alive")
    val textureNameDead: String = if (config.contains("texture_dead")) {
        config.getString("texture_dead")
    } else {
        textureNameAlive
    }
    val textureNameAnimation: String?
    val animationFrameDuration: Float

    init {
        if (config.contains("animation")) {
            val animationParts = config.getString("animation").split(":")
            textureNameAnimation = animationParts[0].trim()
            animationFrameDuration = if (animationParts.size >= 2) {
                animationParts[1].trim().toFloat()
            } else {
                0.1f
            }
        } else {
            textureNameAnimation = null
            animationFrameDuration = 0f
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