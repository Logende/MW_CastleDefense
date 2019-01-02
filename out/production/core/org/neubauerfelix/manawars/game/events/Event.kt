package org.neubauerfelix.manawars.game.events

interface Event {

    fun isCancelled(): Boolean{
        return false
    }

}