package org.neubauerfelix.manawars.game

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

class GameAssetLoader: IAssetLoader, IDisposable {

    private val assetManager: AssetManager = AssetManager()


    override fun dispose() {
        assetManager.dispose()
    }

    override fun loadTexture(fileName: String){
        assetManager.load(fileName, Texture::class.java)
    }

    override fun loadAtlas(fileName: String){
        assetManager.load(fileName, TextureAtlas::class.java)
    }

    override fun unloadAsset(fileName: String): Boolean{
        assetManager.unload(fileName)
        return !assetManager.isLoaded(fileName)
    }

    override fun getTexture(fileName: String): Texture{
        if(!assetManager.isLoaded(fileName)){
            throw RuntimeException("Unable to get texture '" + fileName + "': not loaded.")
        }
        return assetManager.get(fileName, Texture::class.java)
    }

    override fun getAtlas(fileName: String): TextureAtlas{
        if(!assetManager.isLoaded(fileName)){
            throw RuntimeException("Unable to get atlas '" + fileName + "': not loaded.")
        }
        return assetManager.get(fileName, TextureAtlas::class.java)
    }

    override fun areAssetsLoaded(): Boolean{
        return assetManager.update()
    }

    override fun finishLoading(){
        assetManager.finishLoading()
    }

    override fun createTextureRegion(fileName: String, x: Int, y: Int, width: Int, height: Int): TextureRegion{
        val texture = getTexture(fileName)
        return TextureRegion(texture, x, y, width, height)
    }

    override fun createTextureRegion(fileName: String): TextureRegion{
        val texture = getTexture(fileName)
        return TextureRegion(texture)
    }
}