package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.data.units.DataBaseUnitStats
import org.neubauerfelix.manawars.manawars.data.units.IDataBaseUnitStats
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitRarity
import org.neubauerfelix.manawars.manawars.enums.MWUnitType
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration
import java.util.*

class BaseUnitHandler : IBaseUnitHandler, ILoadableContent {

    private val baseUnitStats= EnumMap<MWUnitType, EnumMap<MWUnitRarity, IDataBaseUnitStats>>(MWUnitType::class.java)

    init {
        for (unitType in MWUnitType.values()) {
            baseUnitStats[unitType] = EnumMap(MWUnitRarity::class.java)
        }
    }




    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            val handlerConfigNames = gameConfig.getStringList("stats")
            for (handlerConfigName in handlerConfigNames) {
                val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$handlerConfigName", true)
                for (unitType in MWUnitType.values()) {
                    if (handlerConfig.contains("units." + unitType.name.toLowerCase())) {
                        val unitTypeConfig = handlerConfig.getSection("units").getSection(unitType.name.toLowerCase())

                        for (unitRarity in MWUnitRarity.values()) {
                            if (unitTypeConfig.contains(unitRarity.name.toLowerCase())) {
                                val baseUnitStatsConfig = unitTypeConfig.getSection(unitRarity.name.toLowerCase())
                                val stats = DataBaseUnitStats(baseUnitStatsConfig.getFloat("health"),
                                        baseUnitStatsConfig.getFloat("damage"),
                                        baseUnitStatsConfig.getFloat("cooldown"),
                                        baseUnitStatsConfig.getInt("cost"))
                                baseUnitStats[unitType]!![unitRarity] = stats
                            }
                        }
                    }
                }
            }
        }
    }

    override val loadedContent: Boolean
        get() = baseUnitStats[MWUnitType.BOSS]!!.isNotEmpty()



    override fun getBaseUnitStats(unitType: MWUnitType, unitRarity: MWUnitRarity): IDataBaseUnitStats {
        return baseUnitStats[unitType]!![unitRarity]!!
    }
}