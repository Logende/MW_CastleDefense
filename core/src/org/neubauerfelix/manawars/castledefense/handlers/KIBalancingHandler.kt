package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.castledefense.events.EntityGoldEvent
import org.neubauerfelix.manawars.castledefense.ki.BaseFeatures
import org.neubauerfelix.manawars.castledefense.player.CDControllerBot
import org.neubauerfelix.manawars.game.events.IEvent
import org.neubauerfelix.manawars.game.events.Listener
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.events.EntityDamageEvent
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
                override fun handleEvent(event: IEvent) {
                    val e = event as EntityGoldEvent
                    if (!e.cancelled) {
                        val cdPlayer = e.castle.player
                        if (cdPlayer.controller is CDControllerBot) {

                            val playerEarnsGoldFirst = cdPlayer.castle.direction == 1
                            val goldOffset = if (playerEarnsGoldFirst) 0 else e.goldDifference
                            val worthPlayer = BaseFeatures.entityWorth(cdPlayer, true) +
                                    BaseFeatures.gold(cdPlayer) + goldOffset
                            val worthEnemy = BaseFeatures.entityWorth(cdPlayer.enemy, true) +
                                    BaseFeatures.gold(cdPlayer.enemy)

                            val enemyDominanceRatio = worthEnemy / worthPlayer
                            val helpFactor = min(CDConstants.KI_BALANCING_MAX_HELP_FACTOR,
                                    max(CDConstants.KI_BALANCING_MIN_HELP_FACTOR, enemyDominanceRatio))
                            e.goldDifference = (e.goldDifference * helpFactor).toInt()
                        }
                    }
                }

            })


            MManaWars.m.getEventHandler().registerListener(EntityDamageEvent::class.java.name, object : Listener() {
                override fun handleEvent(event: IEvent) {
                    val e = event as EntityDamageEvent
                    if (!e.cancelled) {
                        if (e.entity is ICDEntityCastle) {
                            val castle = e.entity
                            val cdPlayer = castle.player
                            if (!cdPlayer.controller.playerControlled) {
                                val healthBefore = castle.health
                                val healthAfter = healthBefore - event.damage
                                val supportHealth = castle.healthMax * CDConstants.KI_BALANCING_CASTLE_SUPPORT_HEALTH
                                if (healthAfter > 0 && healthBefore > supportHealth && healthAfter < supportHealth) {
                                    val boss = cdPlayer.tribe.army.units[0]
                                    cdPlayer.spawnUnit(boss)
                                }
                            }
                        }
                    }
                }

            })
        }
    }

}
