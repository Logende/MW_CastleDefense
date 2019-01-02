package org.neubauerfelix.manawars.game

import com.badlogic.gdx.graphics.g2d.Batch

@FunctionalInterface
interface IDrawableComponent {

    fun draw(delta: Float, batcher: Batch, offsetX: Float, offsetY: Float)
}