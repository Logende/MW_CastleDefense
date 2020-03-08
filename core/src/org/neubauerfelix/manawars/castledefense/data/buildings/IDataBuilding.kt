package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.NWRarity

interface IDataBuilding : IDataPresentable {


    // produces and spawns the entity
    fun produce(centreHor: Float, bottom: Float = GameConstants.WORLD_HEIGHT, team: Int, league: IDataLeague): ILiving
    fun produceBuilder(centreHor: Float, bottom: Float = GameConstants.WORLD_HEIGHT, player: ICDPlayer): ILiving

    val health: Float
    val animationProducer: IEntityAnimationProducer

    val name: String
    val cost: Int

    val rarity: NWRarity

}