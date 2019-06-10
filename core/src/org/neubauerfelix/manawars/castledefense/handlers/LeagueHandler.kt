package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.castledefense.data.DataLeagueLoaded
import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class LeagueHandler : ILeagueHandler, ILoadableContent {

    private val leagues = HashMap<String, IDataLeague>()
    override var loadedContent: Boolean = false

    override fun putLeague(league: IDataLeague) {
        leagues[league.name] = league
    }

    override fun getLeague(name: String): IDataLeague? {
        return leagues[name]
    }

    override fun listLeagues(): Iterable<IDataLeague> {
        return leagues.values
    }


    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true

            val handlerConfigNames = gameConfig.getStringList("leagues")
            for (handlerConfigName in handlerConfigNames) {
                val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$handlerConfigName", true)
                this.loadLeague(handlerConfig)
            }
        }
    }


    private fun loadLeague(config: Configuration) {
        val league = DataLeagueLoaded(config)
        println("Loaded league ${league.name}.")
        league.tribes.forEach {
            it.army.units.forEach { unit ->
                MManaWars.m.getUnitHandler().putUnit(unit)
            }
        }
        this.putLeague(league)
    }
}