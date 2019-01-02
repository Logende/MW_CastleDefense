package org.neubauerfelix.manawars.game.events

abstract class Listener {

    abstract fun handleEvent(event: Event)

    fun isDestroyListener(): Boolean{
        return false
    }

}