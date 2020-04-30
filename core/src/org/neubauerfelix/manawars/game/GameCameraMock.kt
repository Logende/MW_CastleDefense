package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IEntity


class GameCameraMock : ICamera {

    override fun render(drawableBackgrounds: Iterable<IDrawable>, drawBackgroundsStatic: Boolean, ingameWindowX: Float, toDraw2Ingame: Iterable<IEntity>, toDraw3Components: Iterable<IDrawableComponent>) {

    }

    override fun renderLoadingScreen(delta: Float) {
    }

    override fun resize(width: Int, height: Int) {
    }

    override val window: GameRectangle = GameRectangle(0f, 0f, 0f, 0f)
}