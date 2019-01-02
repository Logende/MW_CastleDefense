package org.neubauerfelix.manawars.game

import com.badlogic.gdx.utils.viewport.Viewport
import org.neubauerfelix.manawars.game.entities.IEntity

interface ICamera {

    fun render(delta: Float, drawableBackgrounds: Iterable<IDrawable>, drawBackgroundsStatic: Boolean, ingameWindowX: Float,
               toDraw2Ingame: Iterable<IEntity>, toDraw3Components: Iterable<IDrawableComponent>)

    fun renderLoadingScreen(delta: Float)

    fun resize(width: Int, height: Int)

    fun getViewport(): Viewport

}