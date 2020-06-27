package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.IDataPlayground
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration
import java.util.*
import kotlin.collections.ArrayList

class DataTribeLoaded(config: Configuration) : DataTribe() {

    override val name: String = config.getString("name")
    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("tribe_${name}_name")

    init {
        println("Loading tribe $name.")
    }

    override val army: IDataArmy = DataArmyLoaded(config.getSection("army"), this.name, this)
    override val castle: IDataCastle

    init {
        val castleDefinition = config.getString("castle").split(":")
        val castleConfigName = castleDefinition[0]
        val castleMultiplier = castleDefinition[1].toFloat()
        val castleConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).
                load("content/$castleConfigName", true)
        this.castle = DataCastleLoaded(castleConfig, castleMultiplier, CDConstants.CASTLE_HEALTH_BASE,
                CDConstants.CASTLE_MONEY_START_BASE, CDConstants.CASTLE_MONEY_PER_CYCLE_BASE)
    }

    override val backgroundTheme = MWBackgroundTheme.valueOf(
            config.getString("background_theme").toUpperCase(Locale.ROOT)
    )
    override val backgroundSubthemes: List<MWBackgroundSubtheme> = config.getStringList("background_subthemes").
            map { MWBackgroundSubtheme.valueOf(it.toUpperCase(Locale.ROOT)) }

    override val musicTrack: String = config.getString("music")

    override val playground: IDataPlayground = CDManaWars.cd.getPlaygroundListHandler().
            playgrounds[config.getString("playground")]
            ?:error("Playground with name ${config.getString("playground")} not known")

    override val buildings: List<IDataBuilding>

    init {
        buildings = ArrayList()
        val buildingNames = config.getStringList("buildings")
        for (buildingName in buildingNames) {
            val building = CDManaWars.cd.getBuildingListHandler().buildings[buildingName]
                    ?:error("Building with name $buildingName not known.")
            buildings.add(building)
        }
    }
}