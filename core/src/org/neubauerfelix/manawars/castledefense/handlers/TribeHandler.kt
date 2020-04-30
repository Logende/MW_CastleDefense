package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.castledefense.data.tribes.DataTribeLoaded
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class TribeHandler : ITribeHandler, ILoadableContent {

    private val tribes = LinkedHashMap<String, IDataTribe>()
    override var loadedContent: Boolean = false

    override fun putTribe(tribe: IDataTribe) {
        tribes[tribe.name] = tribe
    }

    override fun getTribe(name: String): IDataTribe? {
        return tribes[name]
    }

    override fun listTribes(): Iterable<IDataTribe> {
        return tribes.values
    }


    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true

            val handlerConfigNames = gameConfig.getStringList("tribes")
            for (handlerConfigName in handlerConfigNames) {
                val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).
                        load("content/$handlerConfigName", true)
                this.loadTribe(handlerConfig)
            }
        }
    }


    private fun loadTribe(config: Configuration) {
        val tribe = DataTribeLoaded(config)
        println("Loaded tribe ${tribe.name}.")
        tribe.army.units.forEach { unit ->
                MManaWars.m.getUnitHandler().putUnit(unit)
            }
        this.putTribe(tribe)
    }
}