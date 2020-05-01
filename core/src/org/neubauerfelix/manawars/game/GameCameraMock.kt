package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IEntity


class GameCameraMock : ICamera {

    override fun render(drawableBackgrounds: Iterable<IDrawable>, drawBackgroundsStatic: Boolean, ingameWindowX: Float, drawableIngame: Iterable<IEntity>, drawableComponents: Iterable<IDrawableComponent>, drawComponentsStatic: Boolean) {

    }

    override fun renderLoadingScreen(delta: Float) {
    }

    override fun resize(width: Int, height: Int) {
    }

    override val window: GameRectangle = GameRectangle(0f, 0f, 0f, 0f)

    override fun projectPointOnWindow(x: Float, y: Float): GameLocation {
        return GameLocation(0f, 0f)
    }
}