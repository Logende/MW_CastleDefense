package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.castledefense.data.buildings.DataBuildingActionLoaded
import org.neubauerfelix.manawars.castledefense.data.buildings.DataBuildingPlaceholder
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.castledefense.data.tribes.DataCastleLoaded
import org.neubauerfelix.manawars.castledefense.data.tribes.DataTribeLoaded
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataCastle
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class DataLeagueLoaded(config: Configuration) : IDataLeague, ICastleProvider {

    override val name: String = config.getString("name")
    override val castles: List<IDataCastle>
    override val tribes: List<IDataTribe>
    override val buildings: List<IDataBuilding>
    override val buildingPlaceholder: IDataBuilding
    override val playground: IDataPlayground

    init {
        val buildingPlaceholderTextureName = config.getString("building_placeholder_texture")
        buildingPlaceholder = DataBuildingPlaceholder(buildingPlaceholderTextureName, "${this.name}_placeholder")
    }

    override val baseGoldStart: Float = config.getFloat("castle_gold_start_average")
    override val baseGoldPerSecond: Float = config.getFloat("castle_gold_per_second_average")
    override val baseHealth: Float = config.getFloat("castle_health_average")

    init {
        // castles need to be loaded before tribes, because tribes use castles
        castles = ArrayList()
        val castleConfigNames = config.getStringList("castles")
        for (castleConfigName in castleConfigNames) {
            val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).
                    load("content/$castleConfigName", true)
            castles.add(DataCastleLoaded(handlerConfig, 1f, baseHealth, baseGoldStart, baseGoldPerSecond))
        }

        tribes = ArrayList()
        val tribeConfigNames = config.getStringList("tribes")
        for (tribeConfigName in tribeConfigNames) {
            val tribeConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$tribeConfigName", true)
            val tribe = DataTribeLoaded(tribeConfig, this)
            tribes.add(tribe)
            println("Loaded tribe ${tribe.name}.")
        }

        buildings = ArrayList()
        val buildingConfigNames = config.getStringList("buildings")
        for (buildingConfigName in buildingConfigNames) {
            val buildingConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java)
                    .load("content/$buildingConfigName", true)
            val building = DataBuildingActionLoaded(buildingConfig, buildingConfigName)
            buildings.add(building)
            println("Loaded building ${building.name}.")
        }

        val playgroundConfigSection = config.getSection("playground")
        playground = DataPlaygroundLoaded(playgroundConfigSection)
    }

    override fun getTribe(name: String): IDataTribe? {
        return tribes.firstOrNull { it.name.equals(name, ignoreCase = true) }
    }

    override fun getCastle(name: String): IDataCastle? {
        return castles.firstOrNull { it.name.equals(name, ignoreCase = true) }
    }

    override fun getBuilding(name: String): IDataBuilding? {
        return buildings.firstOrNull { it.name.equals(name, ignoreCase = true) }
    }


}