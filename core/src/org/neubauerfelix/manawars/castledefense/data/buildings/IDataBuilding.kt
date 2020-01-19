package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer

interface IDataBuilding : IAsset {


    fun produce(x: Float, y: Float, team: Int): ILiving

    val health: Float
    val animationProducer: IEntityAnimationProducer

}