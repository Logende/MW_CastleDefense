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
    var loaded: Boolean = false
        private set


    fun addAsset(asset: IAsset) {
        synchronized(assets) {
            synchronized(loadCount) {
                if (assets.contains(asset)) {
                    throw IllegalArgumentException("Can not add asset to GameData: Asset already contained in assets list.")
                }
                assets.add(asset)
                for(i in 1..loadCount) {
                    asset.loadAsset()
                }
            }
        }
    }

    fun removeAsset(asset: IAsset) {
        synchronized(assets) {
            synchronized(loadCount) {
                if (!assets.contains(asset)) {
                    throw IllegalArgumentException("Can not remove asset from GameData: Asset not contained in assets list.")
                }
                assets.remove(asset)
                for(i in 1..loadCount){
                    asset.disposeAsset()
                }
            }
        }
    }

    override fun loadAsset() {
        synchronized(loadCount) {
            synchronized(assets) {
                loadCount++
                this.load()
                assets.onEach { asset -> asset.loadAsset() }
            }
        }
    }

    override fun disposeAsset() {
        synchronized(loaded) {
            synchronized(loadCount) {
                synchronized(assets) {
                    require(loadCount > 0)
                    loadCount--
                    if (loadCount == 0) {
                        loaded = false
                        this.disposed()
                    }
                    this.dispose()
                    assets.onEach { asset -> asset.disposeAsset() }
                }
            }
        }
    }

    override fun loadedAsset() {
        synchronized(loaded) {
            synchronized(loadCount) {
                synchronized(assets) {
                    require(loadCount > 0)
                    assets.onEach { asset -> asset.loadedAsset() }
                    if (!loaded) {
                        this.loaded()
                        loaded = true
                    }
                }
            }
        }
    }




    abstract fun dispose() // decreased load count and disposes of reaching load count 0
    abstract fun load() // increases load count and loads if not loaded already
    abstract fun loaded() // once called when loaded for first time after not loaded / disposed
    abstract fun disposed() // called when load count reaches 0. Used to destroy asset

    /**
     * Called once after GameData instance is created and before it is loaded. Used to add asset dependencies
     * Should be called after all assets of the whole game are instantiated.
     * This way all assets can connect with each other.
     */
    abstract fun init() //
}