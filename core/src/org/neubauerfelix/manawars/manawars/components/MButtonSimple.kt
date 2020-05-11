package org.neubauerfelix.manawars.manawars.components

import com.badlogic.gdx.graphics.g2d.TextureRegion

class MButtonSimple(x: Float, y: Float, width: Float, height: Float, texture: TextureRegion,
                    texturePressed: TextureRegion = texture, private val action: Runnable) :
        MButton(x, y, width, height, texture, texturePressed) {

    override fun clickAction() {
        action.run()
    }

    override fun unclickAction() {
    }
}