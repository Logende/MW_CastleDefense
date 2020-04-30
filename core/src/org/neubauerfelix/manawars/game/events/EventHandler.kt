package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.game.IEventHandler
import org.neubauerfelix.manawars.game.IResetable

class EventHandler: IEventHandler, IResetable {

    // Note that this whole custom event handling code is okay but not ideal.
    // better would be support for event priorities, making it possible that for example one early priority event
    // changes values and an other event can work with the new values. In the current approach, priorities can only
    // be achieved through registering events in the proper order

    private val listeners: MutableMap<String, ArrayList<Listener>> = LinkedHashMap()
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

    override fun removeListener(listener: Listener) {
        var removedCount = 0
        synchronized(listeners) {
            for ((_, list) in listeners) {
                while (list.contains(listener)) {
                    list.remove(listener)
                    removedCount++
                }
            }
        }
        check(removedCount == 1)
    }

    override fun callEvent(event: IEvent){
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