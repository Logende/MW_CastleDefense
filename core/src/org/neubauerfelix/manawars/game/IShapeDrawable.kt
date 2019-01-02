package org.neubauerfelix.manawars.game

import com.badlogic.gdx.graphics.glutils.ShapeRenderer

interface IShapeDrawable {

    fun draw(delta: Float, shapeRenderer: ShapeRenderer)
}