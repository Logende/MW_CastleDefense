package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.components.MComponent

class CDComponentBackground(x: Float, y: Float, width: Float, height: Float, val textureRegion: TextureRegion) :
        MComponent(x, y, width, height) {



    override fun draw(batcher: Batch, offsetX: Float, offsetY: Float) {
        batcher.draw(textureRegion, x + offsetX, y + offsetY, width, height)
    }

    override fun clickAction() {
    }

    override fun unclickAction() {
    }

}

