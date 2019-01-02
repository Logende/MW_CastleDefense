package org.neubauerfelix.manawars.manawars.components


import com.badlogic.gdx.graphics.g2d.TextureRegion

class MImage : MButton {

    constructor(x: Float, y: Float, width: Float, height: Float, texture: TextureRegion) : super(x, y, width, height, texture)


    override fun clickAction() {}

    override fun unclickAction() {}
}
