package org.neubauerfelix.manawars.castledefense.analysis

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.ITeamable
import org.neubauerfelix.manawars.manawars.entities.MSkill

class CDPlayerLiveAnalysis : ICDPlayerLiveAnalysis {

    override var entities: List<IEntity> = arrayListOf()
    override var units: LinkedHashMap<IDataUnit, Int> = linkedMapOf()
    override var skills: List<IEntity> = arrayListOf() // actually just contains MSkill
    override var furthestX: Float = 0f


    override var totalActionValue: Float = 0f
    override var totalDefensiveStrengthPerSecond: Float = 0f
    override var totalOffensiveStrengthPerSecond: Float = 0f
    override var totalSurvivalFactor: Float = 0f
    override var totalHealth: Float = 0f



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