package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildings
import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataCastleLoaded(config: Configuration, league: IDataLeague, multiplier: Float = 1f) : DataCastle() {


    override val buildings: IDataBuildings
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override val name: String = config.getString("name")
    override val textureNameAlive: String = config.getString("texture")
    override val textureNameDead: String = config.getString("texture") + ".damaged"
    override val unitSpawnOffset: ILocated

    init {
        val unitSpawnOffsetParts = config.getString("spawn_offset").split(":")
        val x = unitSpawnOffsetParts[0].toFloat()
        val y = unitSpawnOffsetParts[1].toFloat()
        unitSpawnOffset = GameLocation(x, y)
    }


    override val goldStart: Int = (league.startGoldAvg* multiplier * config.getFloat("goldStart")).toInt()


    override val goldPerCharge: Int = (league.goldPerSecondAvg * multiplier *
            config.getFloat("goldPerCharge") * CDConstants.CASTLEDEFENSE_CASTLE_GOLD_CHARGE_DELAY).toInt()

    init {
        this.goldStart
    }

    override val health: Float  = league.castleHealthAvg * multiplier *
            config.getFloat("health")



}