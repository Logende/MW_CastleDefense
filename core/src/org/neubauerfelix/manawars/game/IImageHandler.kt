package org.neubauerfelix.manawars.game

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion

interface IImageHandler: IHandler {

    fun getTextureRegionMain(path: String): TextureAtlas.AtlasRegion
    fun getTextureRegionsMain(path: String, columns: Int, rows: Int): Array<TextureRegion>
    fun getTextureRegionButton(path: String): TextureAtlas.AtlasRegion
}