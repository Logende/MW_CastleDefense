package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme
import org.neubauerfelix.manawars.manawars.storage.Configuration
import java.util.*

class DataCastleLoaded(config: Configuration, multiplier: Float = 1f,
                       baseCastleHealth: Float, baseGoldStart: Float, baseGoldPerSecond: Float) :
        DataCastle() {


    override val name: String = config.getString("name")

    private val textureNames: Map<MWBackgroundTheme, String>

    init {
        val textureNameDefault = config.getString("textures.default")
        textureNames = EnumMap<MWBackgroundTheme, String>(MWBackgroundTheme::class.java)
        MWBackgroundTheme.values().forEach {
            textureNames[it] = config.getString(
                    "textures.${it.name.toLowerCase(Locale.getDefault())}",
                    textureNameDefault
            )
        }
    }

    override fun getTextureName(theme: MWBackgroundTheme): String {
        return textureNames[theme] ?: error("this should not happen")
    }

    override val unitSpawnXOffset: Float
    override val unitSpawnYOffset: Float

    override val xOffset: Float
    override val yOffset: Float

    init {
        val unitSpawnOffsetParts = config.getString("offset_units").split(":")
        unitSpawnXOffset = unitSpawnOffsetParts[0].toFloat()
        unitSpawnYOffset = unitSpawnOffsetParts[1].toFloat()

        val offset = config.getString("location").split(":")
        xOffset = if (offset[0].isNotEmpty()) { offset[0].toFloat() } else { 0f }
        yOffset = if (offset.size >= 2 && offset[1].isNotEmpty()) { offset[1].toFloat() } else { 0f }
    }


    override val goldStart: Int = (baseGoldStart * multiplier * config.getFloat("goldStart")).toInt()


    override val goldPerCharge: Int = (baseGoldPerSecond * multiplier *
            config.getFloat("goldPerCharge") * CDConstants.CASTLE_GOLD_CHARGE_DELAY).toInt()

    init {
        this.goldStart
    }

    override val health: Float  = baseCastleHealth * multiplier *
            config.getFloat("health")



}