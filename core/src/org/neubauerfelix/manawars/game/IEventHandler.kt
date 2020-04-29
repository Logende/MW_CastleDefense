package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.game.events.IEvent
import org.neubauerfelix.manawars.game.events.Listener

interface IEventHandler: IHandler {

    fun registerListener(eventClassName: String, listener: Listener)
    fun removeListener(eventClassName: String, listener: Listener)
    fun removeListener(listener: Listener)
    fun callEvent(event: IEvent)
}