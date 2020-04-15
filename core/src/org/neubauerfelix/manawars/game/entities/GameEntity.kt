package org.neubauerfelix.manawars.game.entities

import org.neubauerfelix.manawars.game.AManaWars


open class GameEntity(width: Float, height: Float): GameRectangle(width, height), IEntity {

    override var remove: Boolean = false
    override var propertyScale: Float = 1f



    fun pasteEntity(e: IEntity, onlyTemporaryValues: Boolean) {
        super.pasteRectangle(e, onlyTemporaryValues)
        remove = e.remove
        if(!onlyTemporaryValues) {
            propertyScale = e.propertyScale
        }
    }

    override fun spawn() {
        AManaWars.m.screen.addEntity(this)
    }

    override fun destroyed() {
    }
}