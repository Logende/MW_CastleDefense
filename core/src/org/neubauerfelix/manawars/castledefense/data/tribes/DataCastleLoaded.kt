package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme
import org.neubauerfelix.manawars.manawars.storage.Configuration
import java.util.*

class DataCastleLoaded(config: Configuration, multiplier: Float = 1f,
                       baseCastleHealth: Float, baseGoldStart: Float, baseMoneyPerCycle: Float) :
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


    override val moneyStart: Int = (baseGoldStart * multiplier * config.getFloat("moneyStart")).toInt()


    override val moneyPerCycle: Int = (baseMoneyPerCycle * multiplier *
            config.getFloat("moneyPerCycle")).toInt()

    init {
        this.moneyStart
    }

    override val health: Float  = baseCastleHealth * multiplier *
            config.getFloat("health")



}