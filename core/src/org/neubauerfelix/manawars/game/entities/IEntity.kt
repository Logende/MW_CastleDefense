package org.neubauerfelix.manawars.game.entities

interface IEntity: ISized {

    var remove: Boolean
    var propertyScale: Float

    fun spawn()
    fun destroy()
}