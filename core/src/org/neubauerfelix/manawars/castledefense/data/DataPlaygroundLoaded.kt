package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.buildings.DataMineLoaded
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataMine
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.handlers.MathUtils
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataPlaygroundBuilding(
        val name: String,
        val xCentre: Float,
        val yOffset: Float,
        val leftSide: Boolean
)


class DataPlaygroundLoaded(config: Configuration) : IDataPlayground {
    override val name: String = config.getString("name")
    override val backgroundCount: Int = config.getInt("background_count")

    private val buildingPlaceholdersX = arrayListOf<Float>()
    private val buildings = arrayListOf<DataPlaygroundBuilding>()

    private val mine: IDataMine?

    init {
        println("Loading playground $name.")
        val buildingPlaceholderDefinitions = config.getStringList("building_placeholders")
        for (buildingPlaceholderDefinition in buildingPlaceholderDefinitions) {
            val x = MathUtils.calc(buildingPlaceholderDefinition, "playground_width",
                    (backgroundCount * GameConstants.BACKGROUND_WIDTH).toDouble())
            buildingPlaceholdersX.add(x.toFloat())
        }

        val buildingsDefinitions = config.getStringList("buildings")
        for (buildingDefinition in buildingsDefinitions) {
            val parts = buildingDefinition.split(":")
            val buildingName = parts[0]
            val x = MathUtils.calc(parts[1], "playground_width",
                    (backgroundCount * GameConstants.BACKGROUND_WIDTH).toDouble())
            val yOffset = parts[2].toFloat()
            val leftSide = when (parts[3]) {
                "left" -> true
                "right" -> false
                else -> error("Unknown side in building definition: ${parts[3]}. Expected left or right.")
            }
            buildings.add(DataPlaygroundBuilding(buildingName, x.toFloat(), yOffset, leftSide))
        }

        mine = if (config.contains("mine")) {
            DataMineLoaded(config.getSection("mine"))
        } else {
            null
        }

    }

    override fun createPlayground(playerA: ICDPlayer, playerB: ICDPlayer) {
        val buildingHandler = CDManaWars.cd.getBuildingListHandler()
        for (buildingPlaceholderX in buildingPlaceholdersX) {
            buildingHandler.buildingPlaceholder.
                    produce(buildingPlaceholderX, team = MConstants.TEAM_PEACEFUL, direction = 1,
                            spawnPlaceholderOnDeath = true)
        }
        for (building in buildings) {
            val direction = if (building.leftSide) 1 else -1
            val team = if (building.leftSide) MConstants.TEAM_PLAYER else MConstants.TEAM_BOT
            (buildingHandler.buildings[building.name]
                    ?: error("Unknown building in playground definition: ${building.name}")).
                    produce(building.xCentre, bottom = GameConstants.WORLD_HEIGHT_UNITS + building.yOffset,
                            team = team, direction = direction, spawnPlaceholderOnDeath = false)
        }
        if (mine != null) {
            mine.produce(800f, GameConstants.BACKGROUND_HEIGHT - 300f, playerA.castle)
            mine.produce(backgroundCount * GameConstants.BACKGROUND_WIDTH - 800f,
                    GameConstants.BACKGROUND_HEIGHT - 300f, playerB.castle)
        }
    }

}