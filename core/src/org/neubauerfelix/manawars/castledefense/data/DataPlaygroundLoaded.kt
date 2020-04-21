package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.handlers.MathUtils
import org.neubauerfelix.manawars.manawars.storage.Configuration


class DataPlaygroundLoaded(config: Configuration) : IDataPlayground {
    override val name: String = config.getString("name")

    private val buildingPlaceholdersX = ArrayList<Float>()

    init {
        val buildingPlaceholderDefinitions = config.getStringList("building_placeholders")
        for (buildingPlaceholderDefinition in buildingPlaceholderDefinitions) {
            val x = MathUtils.calc(buildingPlaceholderDefinition, "playground_width",
                    IDataPlayground.PLAYGROUND_WIDTH.toDouble())
            buildingPlaceholdersX.add(x.toFloat())
        }
    }

    override fun createPlayground(playerA: ICDPlayer, playerB: ICDPlayer) {
        for (buildingPlaceholderX in buildingPlaceholdersX) {
            CDManaWars.cd.getBuildingListHandler().buildingPlaceholder.
                    produce(buildingPlaceholderX, team = MConstants.TEAM_PEACEFUL, direction = 1)
        }
    }

}