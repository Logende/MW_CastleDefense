package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataCastleLoaded(config: Configuration) : DataCastle() {

    override val textureName: String = "castles/${config.getString("texture")}.png"
    override val unitSpawnOffset: ILocated

    init {
        val unitSpawnOffsetParts = config.getString("spawn_offset").split(":")
        val x = unitSpawnOffsetParts[0].toFloat()
        val y = unitSpawnOffsetParts[1].toFloat()
        unitSpawnOffset = GameLocation(x, y)
    }


    override val goldStart: Int = (config.getFloat("goldStart") * MConstants.CASTLE_AVG_GOLD_START).toInt()
    override val goldPerCharge: Int = (config.getFloat("goldPerCharge") * MConstants.CASTLE_AVG_GOLD_PER_CHARGE).toInt()
    override val health: Float = config.getFloat("health") * MConstants.CASTLE_AVG_HEALTH
}