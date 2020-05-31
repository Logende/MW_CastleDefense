package org.neubauerfelix.manawars.manawars.components


import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.manawars.handlers.FontHandler.MWFont

class MTextLabel : MTextButton {

    constructor(x: Float, y: Float, text: String) : super(x, y, text)

    @JvmOverloads constructor(x: Float, y: Float, text: String, font: MWFont, scale: Float = 1f) : super(x, y, text, font, scale)

    override fun clickAction() {}

    override fun unclickAction() {
    }

    override fun intendedUserAction() {
    }


    override fun draw(batcher: Batch, offsetX: Float, offsetY: Float) {
        //batcher.draw(GameImageHandler.imageHandler!!.getTextureRegionButton("menu"), offsetX + x, offsetY + y, width, height)
        super.draw(batcher, offsetX, offsetY)
    }
}
