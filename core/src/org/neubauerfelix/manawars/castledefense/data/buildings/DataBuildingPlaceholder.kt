package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingPlaceholder
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.building.EntityAnimationProducerBuilding
import org.neubauerfelix.manawars.manawars.enums.NWRarity

class DataBuildingPlaceholder(textureName: String, override val name: String) :
        IDataBuilding {

    override val health: Float = 1f

    override val animation: IEntityAnimationProducer =
            EntityAnimationProducerBuilding(textureName, textureName,
                    null, 0f)

    override fun produce(centreHor: Float, bottom: Float, team: Int, direction: Int, spawnPlaceholderOnDeath: Boolean): ILiving {
        val e =  CDEntityBuildingPlaceholder(this)
        e.centerHorizontal = centreHor
        e.bottom = bottom
        e.team = team
        e.spawn()
        return e
    }

    override val cost: Int = -1

    override fun produceBuilder(centreHor: Float, bottom: Float, player: ICDPlayer): ILiving {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val rarity: NWRarity = NWRarity.COMMON

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(x: Float, y: Float, width: Float, height: Float, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val action: IDataAction
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
}