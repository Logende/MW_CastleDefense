package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.castledefense.data.DataPlaygroundLoaded
import org.neubauerfelix.manawars.castledefense.data.IDataPlayground
import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration


class PlaygroundListHandler: IPlaygroundListHandler, ILoadableContent {

    override val playgrounds: MutableMap<String, IDataPlayground> = LinkedHashMap()
    override var loadedContent: Boolean = false




    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true
            val playgroundConfigNames = gameConfig.getStringList("playgrounds")
            for (playgroundConfigName in playgroundConfigNames) {
                val playgroundConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).
                        load("content/$playgroundConfigName", true)
                val playground = DataPlaygroundLoaded(playgroundConfig)
                playgrounds[playground.name] = playground
            }
        }
    }
}
