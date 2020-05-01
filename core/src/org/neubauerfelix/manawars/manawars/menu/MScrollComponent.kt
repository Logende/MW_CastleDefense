package org.neubauerfelix.manawars.manawars.menu

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.manawars.MManaWars
import kotlin.math.max
import kotlin.math.min

class MScrollComponent(x: Float, y: Float, width: Float, height: Float) : GameRectangle(x, y, width, height),
        IComponent {

    override fun unclick() {
    }

    override fun isHidden(): Boolean {
        return false
    }

    override fun isPressed(): Boolean {
        return false
    }

    override fun touch(x: Float, y: Float, pointerId: Int): Boolean {
        return false
    }

    override fun release(x: Float, y: Float, pointerId: Int): Boolean {
        return false
    }

    override fun drag(x: Float, y: Float, previousX: Float, previousY: Float): Boolean {
        val yDiff = y - previousY
        val window = MManaWars.m.getCamera().window
        window.y = max(0f, window.y - yDiff)
        window.bottom = min(this.height, window.bottom)
        return true
    }

    override fun draw(batcher: Batch, offsetX: Float, offsetY: Float) {

    }
}