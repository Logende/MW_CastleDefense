package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.game.entities.GameLocation


interface IPercentageCondition {

    val needed: Float
    val available: Float

    val locked: Boolean
        get() = needed > available

    companion object {
        fun drawProgressbar(renderer: ShapeRenderer, center: GameLocation, radius: Float,
                            condition: IPercentageCondition, lastClick: Long) {
            renderer.color = Color(0f, 0f, 0f, 0.5f)
            val needed = condition.needed
            val available = condition.available
            if (needed > available) {
                val lockPercentage = available / needed
                val start = 360f * lockPercentage
                val diff = 360f - start
                renderer.arc(center.x, center.y, radius, 270 - diff, diff)
            } else if (System.currentTimeMillis() - 100 < lastClick) {
                renderer.arc(center.x, center.y, radius, 0f, 360f)
            }
            renderer.color = Color.WHITE
        }
    }
}