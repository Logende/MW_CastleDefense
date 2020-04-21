package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.components.CDComponentBuilding
import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingAction
import org.neubauerfelix.manawars.castledefense.entities.controller.ControllerBuilder
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding
import org.neubauerfelix.manawars.manawars.enums.MWUnitType
import org.neubauerfelix.manawars.manawars.enums.NWRarity
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataBuildingActionLoaded(config: Configuration) :
        IDataBuildingAction {

    override val name: String = config.getString("name")
    override val action: IDataAction = MManaWars.m.getActionHandler().
            loadAction("action_building_$name", config.getSection("action"))!!
    override var actionCooldown: Float = config.getFloat("cooldown")
    override val health: Float = config.getFloat("health")
    override val cost: Int = config.getInt("cost")

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

    override val animation: IEntityAnimationProducer =
            EntityAnimationProducerBuilding(textureNameAlive, textureNameDead,
                    textureNameAnimation, animationFrameDuration)

    override val rarity: NWRarity = NWRarity.valueOf(config.getString("rarity").toUpperCase())
    private val dataBuilder = DataUnitBuilder(this, this.cost, this.rarity)

    override val unitType: MWUnitType = MWUnitType.BUILDING

    override fun produce(centreHor: Float, bottom: Float, team: Int, direction: Int): ILiving {
        val e =  CDEntityBuildingAction(this)
        e.centerHorizontal = centreHor
        e.bottom = bottom
        e.direction = direction
        e.team = team
        e.spawn()
        return e
    }

    override fun produceBuilder(centreHor: Float, bottom: Float, player: ICDPlayer): ILiving {
        return dataBuilder.produce(centreHor, bottom, ControllerBuilder(player), player.team)
    }

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(x: Float, y: Float, width: Float, height: Float, action: Runnable): IComponent {
        return CDComponentBuilding(x, y, width, height, this, action)
    }
}