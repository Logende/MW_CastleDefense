package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.manawars.storage.Configuration

interface ILoadableContent {

    fun loadContent(gameconfig: Configuration)
}