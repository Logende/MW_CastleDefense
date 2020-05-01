package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IEntity

interface ICamera : IHandler {

    fun render(drawableBackgrounds: Iterable<IDrawable>, drawBackgroundsStatic: Boolean, ingameWindowX: Float, drawableIngame: Iterable<IEntity>,
               drawableComponents: Iterable<IDrawableComponent>,
               drawComponentsStatic: Boolean)

    fun renderLoadingScreen(delta: Float)

    fun resize(width: Int, height: Int)

    val window: GameRectangle

    fun projectPointOnWindow(x: Float, y: Float) : GameLocation

}