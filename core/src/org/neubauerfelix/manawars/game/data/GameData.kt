package org.neubauerfelix.manawars.game.data


/**
 * GameData hold data about game object classes, such as skills, units and tribes.
 * It is common to load GameData once when the game is initially loaded, however it also is possible to dynamically
 * load or modify data classes if there is a special need for it.
 * Some GameData act as factory and allow creating game objects of their kind.
 */
abstract class GameData: IAsset{


    private var loadCount: Int = 0
    private val assets: MutableList<IAsset> = ArrayList()


    @Synchronized private fun addAsset(asset: IAsset){
        synchronized(assets){
            if(assets.contains(asset)){
                throw IllegalArgumentException("Can not add asset to GameData: Asset already contained in assets list.")
            }
            assets.add(asset)
        }
        for(i in 1..loadCount){
            asset.loadAsset()
        }
    }

    @Synchronized private fun removeAsset(asset: IAsset){
        synchronized(assets){
            if(!assets.contains(asset)){
                throw IllegalArgumentException("Can not remove asset from GameData: Asset not contained in assets list.")
            }
            assets.remove(asset)
        }
        for(i in 1..loadCount){
            asset.disposeAsset()
        }
    }

    @Synchronized override fun loadAsset() {
        loadCount++
        synchronized(assets){
            assets.onEach { asset -> asset.loadAsset() }
        }
    }

    @Synchronized override fun disposeAsset() {
        if(loadCount == 0){
            throw RuntimeException("Can not dispose asset: zero times loaded.")
        }
        loadCount--
        synchronized(assets){
            assets.onEach { asset -> asset.disposeAsset() }
        }
    }
}