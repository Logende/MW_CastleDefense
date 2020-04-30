package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.castledefense.data.buildings.DataBuildingActionLoaded
import org.neubauerfelix.manawars.castledefense.data.buildings.DataBuildingPlaceholder
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration


class BuildingListHandler: IBuildingListHandler, ILoadableContent {

    override val buildings: MutableMap<String, IDataBuilding> = LinkedHashMap()
    override var loadedContent: Boolean = false
    override lateinit var buildingPlaceholder: DataBuildingPlaceholder




    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true
            buildingPlaceholder = DataBuildingPlaceholder(gameConfig.getString("building_placeholder_texture"),
                    "placeholder")

            val buildingConfigNames = gameConfig.getStringList("buildings")
            for (buildingConfigName in buildingConfigNames) {
                val buildingConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).
                        load("content/$buildingConfigName", true)
                val building = DataBuildingActionLoaded(buildingConfig)
                buildings[building.name] = building
            }
        }
    }
}
