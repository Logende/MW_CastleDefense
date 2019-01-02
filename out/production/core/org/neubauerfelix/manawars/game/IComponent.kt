package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.game.entities.ISized

interface IComponent: IDrawableComponent, ISized {

    fun unclick()
    fun isHidden(): Boolean
    fun isPressed(): Boolean
    fun touch(x: Float, y: Float, pointerId: Int): Boolean
    fun release(x: Float, y: Float, pointerId: Int): Boolean
    fun drag(x: Float, y: Float, pointerId: Int): Boolean
}