package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IEntity

interface ICamera : IHandler {

    fun render(drawableBackgrounds: Iterable<IDrawable>, drawBackgroundsStatic: Boolean, ingameWindowX: Float, toDraw2Ingame: Iterable<IEntity>,
               toDraw3Components: Iterable<IDrawableComponent>)

    fun renderLoadingScreen(delta: Float)

    fun resize(width: Int, height: Int)

    val window: GameRectangle

}