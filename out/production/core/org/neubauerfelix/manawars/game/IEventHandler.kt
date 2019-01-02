package org.neubauerfelix.manawars.game

import org.neubauerfelix.manawars.game.events.Event
import org.neubauerfelix.manawars.game.events.Listener

interface IEventHandler: IHandler {

    fun registerListener(eventClass: Class<Event>, listener: Listener)
    fun callEvent(event: Event)
}