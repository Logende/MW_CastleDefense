package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.data.IDataCoreEntity
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.enums.NWRarity

interface IDataBuilding : IDataPresentable, IDataCoreEntity {


    // produces and spawns the entity
    fun produce(centreHor: Float, bottom: Float = GameConstants.WORLD_HEIGHT_UNITS, team: Int, direction: Int,
                spawnPlaceholderOnDeath: Boolean): ILiving
    fun produceBuilder(centreHor: Float, bottom: Float = GameConstants.WORLD_HEIGHT_UNITS, player: ICDPlayer): ILiving

    val health: Float

    val name: String
    val rarity: NWRarity

}