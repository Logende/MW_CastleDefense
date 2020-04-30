package org.neubauerfelix.manawars.game

import com.badlogic.gdx.graphics.g2d.Batch

@FunctionalInterface
interface IDrawableComponent {

    fun draw(batcher: Batch, offsetX: Float, offsetY: Float)
}