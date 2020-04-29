package org.neubauerfelix.manawars.game.events

abstract class Listener {

    abstract fun handleEvent(event: IEvent)

    fun isDestroyListener(): Boolean{
        return false
    }

}