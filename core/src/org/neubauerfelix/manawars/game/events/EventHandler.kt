package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.game.IEventHandler
import org.neubauerfelix.manawars.game.IResetable

class EventHandler: IEventHandler, IResetable {

    private val listeners: MutableMap<Class<Event>, ArrayList<Listener>> = HashMap()
    private val listenersToRemove: ArrayList<Listener> = ArrayList()


    override fun registerListener(eventClass: Class<Event>, listener: Listener){
        synchronized(listeners){
            if(!listeners.containsKey(eventClass)){
                listeners.put(eventClass, ArrayList())
            }
            listeners.get(eventClass)!!.add(listener)
        }
    }

    override fun callEvent(event: Event){
        synchronized(listeners) {
            if (listeners.containsKey(event.javaClass)) {
                for (listener in listeners.get(event.javaClass)!!) {
                    listener.handleEvent(event)
                    if (listener.isDestroyListener()) {
                        listenersToRemove.add(listener)
                    }
                }
            }
            synchronized(listenersToRemove) {
                if (!listenersToRemove.isEmpty()) {
                    for (listenerToRemove in listenersToRemove) {
                        listeners.get(event.javaClass)!!.remove(listenerToRemove)
                    }
                    listenersToRemove.clear()
                }
            }
        }
    }

    override fun reset() {
        synchronized(listeners){
            listeners.clear()
        }
        synchronized(listenersToRemove){
            listenersToRemove.clear()
        }
    }
}