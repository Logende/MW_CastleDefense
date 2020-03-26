package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.data.DataBackground
import org.neubauerfelix.manawars.manawars.data.IDataBackground
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration


class BackgroundListHandler: IBackgroundListHandler, ILoadableContent {

    override val backgrounds = ArrayList<IDataBackground>()
    override var loadedContent: Boolean = false




    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true
            val handlerConfigNames = gameConfig.getStringList("backgrounds")
            for (handlerConfigName in handlerConfigNames) {
                val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$handlerConfigName", true)
                for (backgroundName in handlerConfig.getStringList("backgrounds")) {
                    backgrounds.add(DataBackground(backgroundName, false))
                    backgrounds.add(DataBackground(backgroundName, true))
                }
            }
        }
    }
}
