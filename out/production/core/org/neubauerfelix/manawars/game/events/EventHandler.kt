package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.game.IEventHandler
import org.neubauerfelix.manawars.game.IResetable

class EventHandler: IEventHandler, IResetable {

    private val listeners: MutableMap<String, ArrayList<Listener>> = HashMap()
    private val listenersToRemove: ArrayList<Listener> = ArrayList()

    override fun registerListener(eventClassName: String, listener: Listener) {
        synchronized(listeners){
            if(!listeners.containsKey(eventClassName)){
                listeners.put(eventClassName, ArrayList())
            }
            listeners.get(eventClassName)!!.add(listener)
        }
    }

    override fun removeListener(eventClassName: String, listener: Listener) {
        synchronized(listeners) {
            require(listeners.containsKey(eventClassName))
            require(listeners.get(eventClassName)!!.contains(listener))
            listeners.get(eventClassName)!!.remove(listener)
        }
    }

    override fun callEvent(event: Event){
        synchronized(listeners) {
            if (listeners.containsKey(event.javaClass.name)) {
                for (listener in listeners.get(event.javaClass.name)!!) {
                    listener.handleEvent(event)
                    if (listener.isDestroyListener()) {
                        listenersToRemove.add(listener)
                    }
                }
            }
            synchronized(listenersToRemove) {
                if (!listenersToRemove.isEmpty()) {
                    for (listenerToRemove in listenersToRemove) {
                        listeners.get(event.javaClass.name)!!.remove(listenerToRemove)
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