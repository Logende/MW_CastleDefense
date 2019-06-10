package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.units.DataUnitUpgrade
import org.neubauerfelix.manawars.manawars.enums.MWUpgrade
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataCastleLoaded(config: Configuration, multiplier: Float = 1f) : DataCastle() {


    override val buildings: IDataBuildings
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override val name: String = config.getString("name")
    override val textureName: String = "castles/${config.getString("texture")}.png"
    override val unitSpawnOffset: ILocated

    init {
        val unitSpawnOffsetParts = config.getString("spawn_offset").split(":")
        val x = unitSpawnOffsetParts[0].toFloat()
        val y = unitSpawnOffsetParts[1].toFloat()
        unitSpawnOffset = GameLocation(x, y)
    }


    override val goldStart: Int = ((MConstants.CASTLE_GOLD_START_MIN +
            (MConstants.CASTLE_GOLD_START_MAX - MConstants.CASTLE_GOLD_START_MIN) * multiplier) *
            config.getFloat("goldStart")).toInt()


    override val goldPerCharge: Int = ((MConstants.CASTLE_GOLD_PER_SEC_MIN +
            (MConstants.CASTLE_GOLD_PER_SEC_MAX - MConstants.CASTLE_GOLD_PER_SEC_MIN) * multiplier) *
            config.getFloat("goldPerCharge") * CDConstants.CASTLEDEFENSE_CASTLE_GOLD_CHARGE_DELAY).toInt()

    init {
        this.goldStart
    }

    override val health: Float  = (MConstants.CASTLE_HEALTH_MIN +
            (MConstants.CASTLE_HEALTH_MAX - MConstants.CASTLE_HEALTH_MIN) * multiplier) *
            config.getFloat("health")



}