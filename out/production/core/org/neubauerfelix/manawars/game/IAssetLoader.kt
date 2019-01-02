package org.neubauerfelix.manawars.game

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface IAssetLoader: IHandler {

    fun loadTexture(fileName: String)
    fun loadAtlas(fileName: String)
    fun unloadAsset(fileName: String): Boolean
    fun getTexture(fileName: String): Texture
    fun getAtlas(fileName: String): TextureAtlas
    fun areAssetsLoaded(): Boolean
    fun finishLoading()
    fun createTextureRegion(fileName: String, x: Int, y: Int, width: Int, height: Int): TextureRegion
    fun createTextureRegion(fileName: String): TextureRegion
}