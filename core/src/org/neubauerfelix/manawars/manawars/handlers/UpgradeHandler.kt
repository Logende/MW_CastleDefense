package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.game.ILoadable
import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.game.events.EntityDamageEvent
import org.neubauerfelix.manawars.game.events.Event
import org.neubauerfelix.manawars.game.events.Listener
import org.neubauerfelix.manawars.manawars.entities.IUpgraded
import org.neubauerfelix.manawars.manawars.enums.MWShield
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

import java.util.HashMap

class UpgradeHandler: IHandler, ILoadable {

    override fun load() {

        // Drain effect
        AManaWars.m.getEventHandler().registerListener(EntityDamageEvent::class.java.name, object: Listener() {
            override fun handleEvent(event: Event) {
                val e = event as EntityDamageEvent
                if (e.damager is IUpgraded) {
                    if (e.damager.drainMultiplier > 0) {
                        e.damager.heal(e.damage * e.damager.drainMultiplier)
                    }
                }
            }
        })

        // TODO: Add support for other upgrades
    }
}
