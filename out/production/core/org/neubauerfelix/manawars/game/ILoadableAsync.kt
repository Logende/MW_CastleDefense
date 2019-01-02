package org.neubauerfelix.manawars.game

interface ILoadableAsync: ILoadable {

    fun loadedAssets()
    fun isLoaded(): Boolean
}