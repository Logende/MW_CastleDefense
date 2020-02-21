package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer

interface IDataBuilding : IDataPresentable {


    // produces and spawns the entity
    fun produce(centreHor: Float, bottom: Float = GameConstants.WORLD_HEIGHT, team: Int): ILiving

    val health: Float
    val animationProducer: IEntityAnimationProducer

    val name: String
    val cost: Int

}