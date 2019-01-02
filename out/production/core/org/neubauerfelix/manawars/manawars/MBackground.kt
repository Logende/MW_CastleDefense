package org.neubauerfelix.manawars.manawars


import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.*

class MBackground(fileName: String, x: Int, flipped: Boolean, assetLoader: IAssetLoader) : IBackground{

    private val assetLoader: IAssetLoader
    private val fileName: String
    private var textureRegion: TextureRegion?
    private val x: Float
    private val flipped: Boolean

    val nextX: Float
        get() = this.x + GameConstants.BACKGROUND_WIDTH

    fun getX(): Float {
        return x
    }

    override fun isLoaded(): Boolean {
        return textureRegion != null;
    }

    init {
        this.assetLoader = assetLoader
        this.fileName = fileName
        this.x = x.toFloat()
        this.textureRegion = null
        this.flipped = flipped
    }


    override fun dispose() {
        assetLoader.unloadAsset(fileName)
    }

    override fun load() {
        assetLoader.loadTexture(fileName)
    }

    override fun loadedAssets() {
        if(!isLoaded()) {
            val texture = assetLoader.getTexture(fileName)
            this.textureRegion = TextureRegion(texture)
        }
    }


    override fun draw(delta: Float, batcher: Batch) {
        batcher.draw(textureRegion, x, GameConstants.CONTROLPANEL_HEIGHT, GameConstants.BACKGROUND_WIDTH, GameConstants.BACKGROUND_HEIGHT)
    }


}