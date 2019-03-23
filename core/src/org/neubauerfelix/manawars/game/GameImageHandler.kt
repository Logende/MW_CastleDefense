package org.neubauerfelix.manawars.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

class GameImageHandler(assetLoader: IAssetLoader): IImageHandler, ILoadableAsync, IDisposable {

    companion object {
        var imageHandler: IImageHandler? = null
    }

    private val assetLoader = assetLoader
    private var main: TextureAtlas? = null
    private var buttons: TextureAtlas? = null
    private var loaded: Boolean = false

    override fun load() {
        imageHandler = this
        assetLoader.loadAtlas(GameConstants.PATH_ATLAS_BUTTONS)
        assetLoader.loadAtlas(GameConstants.PATH_ATLAS_MAIN)
    }

    override fun loadedAssets() {
        if(!loaded) {
            main = assetLoader.getAtlas(GameConstants.PATH_ATLAS_MAIN)
            for (t in main!!.textures) {
                t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
            }
            buttons = assetLoader.getAtlas(GameConstants.PATH_ATLAS_BUTTONS)
            for (t in buttons!!.textures) {
                t.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest)
            }
            for (r in main!!.regions) {
                r.flip(false, true)
            }
            for (r in buttons!!.regions) {
                r.flip(false, true)
            }
            loaded = true
        }
    }

    override fun isLoaded(): Boolean {
        return loaded
    }

    override fun dispose() {
        assetLoader.unloadAsset(GameConstants.PATH_ATLAS_BUTTONS)
        assetLoader.unloadAsset(GameConstants.PATH_ATLAS_MAIN)
    }


    override fun getTextureRegionMain(path: String): TextureAtlas.AtlasRegion {
        return main!!.findRegion(path)
                ?: throw NullPointerException("Image in main package with path '$path' not existing.")
    }


    override fun getTextureRegionsMain(path: String, columns: Int, rows: Int): Array<TextureRegion> {
        val texture = getTextureRegionMain(path)
        val width = texture.regionWidth / columns
        val height = texture.regionHeight / rows
        return Array(columns * rows) {
            i ->
            val x = i % columns
            val y = i / columns
            val r = TextureRegion(texture, x * width, y * height, width, -height)
            r
        }
    }

    override fun getTextureRegionButton(path: String): TextureAtlas.AtlasRegion {
        var region: TextureAtlas.AtlasRegion? = buttons!!.findRegion(path)
        if (region == null) {
            region = buttons!!.findRegion("unknown")!!
        }
        return region
    }
}