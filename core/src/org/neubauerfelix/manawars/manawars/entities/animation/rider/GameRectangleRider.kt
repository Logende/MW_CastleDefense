package org.neubauerfelix.manawars.manawars.entities.animation.rider

import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.manawars.entities.ILooking
import org.neubauerfelix.manawars.manawars.entities.IRideable

class GameRectangleRider(x: Float, y: Float, width: Float, height: Float) : GameRectangle(x, y, width, height), ILooking, IRideable {

    override var direction: Int = 1
    override val riding: Boolean
        get() = true
}