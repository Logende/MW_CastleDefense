package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.castledefense.events.EntityGoldEvent
import org.neubauerfelix.manawars.castledefense.ki.features.BaseFeatures
import org.neubauerfelix.manawars.castledefense.player.CDControllerBot
import org.neubauerfelix.manawars.game.events.Event
import org.neubauerfelix.manawars.game.events.Listener
import org.neubauerfelix.manawars.manawars.MManaWars
import kotlin.math.max
import kotlin.math.min


class KIBalancingHandler: IHandler {

    init {

        /**
         * Adapts how much gold a bot earns, depending on how superior/inferior his worth is compared to the one of
         * the enemy. This balances the game by weakening too strong bots and strengthening weak bots.
         */
        if (CDConstants.KI_BALANCING_ENABLED) {
            MManaWars.m.getEventHandler().registerListener(EntityGoldEvent::class.java.name, object : Listener() {
                override fun handleEvent(event: Event) {
                    val e = event as EntityGoldEvent
                    if (!e.cancelled) {
                        val cdPlayer = e.castle.player
                        if (cdPlayer.controller is CDControllerBot) {
                            val enemyDominanceRatio = BaseFeatures.dominanceRatio(cdPlayer.enemy)
                            val helpFactor = min(CDConstants.KI_BALANCING_MAX_HELP_FACTOR,
                                    max(CDConstants.KI_BALANCING_MIN_HELP_FACTOR, enemyDominanceRatio))
                            e.goldDifference = (e.goldDifference * helpFactor).toInt()
                        }
                    }
                }

            })
        }
    }

}
