package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.MComponent

class CDComponentButtonImage(x: Float, y: Float, width: Float, height: Float, textureRegion: TextureRegion,
                             private val color: Color? = null, private val listener: Runnable) :
        MComponent(x, y, width, height) {


    private val background: TextureRegion = MManaWars.m.getImageHandler().getTextureRegionButton("frame.background")
    private val main: TextureRegion = textureRegion


    override fun draw(batcher: Batch, offsetX: Float, offsetY: Float) {
        batcher.draw(background, x, y, width, height)
        MManaWars.m.getCharacterBarHandler().drawFrame(batcher, x, y, width, height, color)
        batcher.draw(main, x, y, width, height)
    }

    override fun clickAction() {
        listener.run()
    }

    override fun unclickAction() {
    }

}

