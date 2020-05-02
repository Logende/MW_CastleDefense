package org.neubauerfelix.manawars.manawars


import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.*

class MBackground(val fileName: String, val x: Float, val flipped: Boolean, val assetLoader: IAssetLoader) : IBackground {

    var textureRegion: TextureRegion? = null
        private set

    val nextX: Float
        get() = this.x + GameConstants.BACKGROUND_WIDTH

    override fun isLoaded(): Boolean {
        return textureRegion != null;
    }


    override fun dispose() {
        assetLoader.unloadAsset(fileName)
    }

    override fun load() {
        assetLoader.loadTexture(fileName)
    }

    override fun loadedAssets() {
        if (!isLoaded()) {
            val texture = assetLoader.getTexture(fileName)
            this.textureRegion = TextureRegion(texture)
            this.textureRegion!!.flip(flipped, true)
        }
    }


    override fun draw(batcher: Batch) {
        batcher.draw(textureRegion, x, 0f, GameConstants.BACKGROUND_WIDTH, GameConstants.BACKGROUND_HEIGHT)
    }


}