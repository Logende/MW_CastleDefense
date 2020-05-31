package org.neubauerfelix.manawars.manawars.components


import com.badlogic.gdx.graphics.g2d.TextureRegion

// Not clickables
open class MImage : MButton {

    constructor(x: Float, y: Float, width: Float, height: Float, texture: TextureRegion) : super(x, y, width, height, texture)


    override fun clickAction() {
    }


    override fun unclickAction() {
    }

    override fun intendedUserAction() {
    }


    override fun touch(x: Float, y: Float, pointerId: Int): Boolean {
        return false
    }

    override fun release(x: Float, y: Float, pointerId: Int): Boolean {
        return false
    }

}
