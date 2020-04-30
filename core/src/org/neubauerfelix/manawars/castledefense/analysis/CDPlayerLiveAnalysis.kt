package org.neubauerfelix.manawars.castledefense.analysis

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.events.IEvent
import org.neubauerfelix.manawars.game.events.Listener
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.ITeamable
import org.neubauerfelix.manawars.manawars.entities.MSkill
import org.neubauerfelix.manawars.manawars.events.EntityDamageEvent
import org.neubauerfelix.manawars.manawars.events.EntityDeathEvent

class CDPlayerLiveAnalysis : ICDPlayerLiveAnalysis {

    companion object {
        const val EVENT_TIMEFRAME = 3000L
    }

    override var entities: List<IEntity> = arrayListOf()
    override var units: LinkedHashMap<IDataUnit, Int> = linkedMapOf()
    override var skills: List<IEntity> = arrayListOf() // actually just contains MSkill
    override var furthestX: Float = -1f


    override var totalActionValue: Float = 0f
    override var totalDefensiveStrengthPerSecond: Float = 0f
    override var totalOffensiveStrengthPerSecond: Float = 0f
    override var totalSurvivalFactor: Float = 0f
    override var totalHealth: Float = 0f
    override val recentEvents: MutableList<IEvent> = arrayListOf()

    val listeners = arrayListOf<Listener>()


    override fun load() {
        val listenerEntityDamage = object : Listener() {
            override fun handleEvent(event: IEvent) {
                val e = event as EntityDamageEvent
                if (!e.cancelled) {
                    recentEvents.add(e)
                }
            }
        }
        MManaWars.m.getEventHandler().registerListener(EntityDamageEvent::class.java.name, listenerEntityDamage)
        listeners.add(listenerEntityDamage)

        val listenerEntityDeath = object : Listener() {
            override fun handleEvent(event: IEvent) {
                val e = event as EntityDeathEvent
                if (!e.cancelled) {
                    recentEvents.add(e)
                }
            }
        }
        MManaWars.m.getEventHandler().registerListener(EntityDeathEvent::class.java.name, listenerEntityDeath)
        listeners.add(listenerEntityDeath)
    }

    override fun dispose() {
        for (listener in listeners) {
            MManaWars.m.getEventHandler().removeListener(listener)
        }
        listeners.clear()
    }

    override fun update(player: ICDPlayer) {
        this.entities = MManaWars.m.screen.getEntities { (it is ITeamable && it is ILiving) && it.team == player.team}.
                sortedByDescending { it.getDistanceHor(player.castle) }

        synchronized(units) {
            this.units.clear()
            for (e in entities.filterIsInstance(IControlled::class.java)) {
                val unitCount = if (units.containsKey(e.data)) {
                    units[e.data]!! +1
                } else {
                    1
                }
                units[e.data] = unitCount
            }
        }

        this.skills = MManaWars.m.screen.getEntities {
            var result = false
            if (it is MSkill) {
                val owner = it.owner
                if (owner is ITeamable && owner.team == player.team) {
                    result = true
                }
            }
            result
        }.sortedByDescending { it.getDistanceHor(player.castle) }

        val furthestEntity = if (entities.isEmpty()) player.castle else entities.first()
        this.furthestX = if (player.castle.direction == 1) furthestEntity.right else furthestEntity.left

        val gametime = MManaWars.m.screen.getGameTime()
        this.recentEvents.removeIf { it.gametime < gametime - EVENT_TIMEFRAME }


/*
        totalActionValue = 0f
        synchronized(totalDefensiveStrengthPerSecond) {
            synchronized(totalOffensiveStrengthPerSecond) {
                synchronized(totalHealth) {
                    synchronized(totalSurvivalFactor) {
                        synchronized(totalActionValue) {
                            totalDefensiveStrengthPerSecond = 0f
                            totalOffensiveStrengthPerSecond = 0f
                            totalSurvivalFactor = 1f
                            totalHealth = 0f
                            this.entities.filterIsInstance(IControlled::class.java).forEach {
                                val unitAnalysis = it.data.analysis
                                totalActionValue += unitAnalysis.actionValue
                                totalDefensiveStrengthPerSecond += unitAnalysis.defensiveStrengthPerSecond
                                totalOffensiveStrengthPerSecond += unitAnalysis.offensiveStrengthPerSecond
                                totalSurvivalFactor *= unitAnalysis.survivalFactor
                                totalHealth += it.health
                            }
                        }
                    }
                }
            }

        }*/
    }
}