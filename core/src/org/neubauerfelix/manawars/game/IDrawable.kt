package org.neubauerfelix.manawars.game

import com.badlogic.gdx.graphics.g2d.Batch

@FunctionalInterface
interface IDrawable {

    fun draw(delta: Float, batcher: Batch)
}