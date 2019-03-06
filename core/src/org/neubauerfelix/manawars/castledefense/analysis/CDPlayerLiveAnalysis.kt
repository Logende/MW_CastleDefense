package org.neubauerfelix.manawars.castledefense.analysis

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.ITeamable
import org.neubauerfelix.manawars.manawars.entities.MSkill

class CDPlayerLiveAnalysis : ICDPlayerLiveAnalysis {

    override lateinit var entities: List<IEntity> // actually contains just IControlled
    override lateinit var skills: List<IEntity> // actually just contains MSkill
    override var furthestX: Float = 0f


    override var totalActionValue: Float = 0f
    override var totalDefensiveStrengthPerSecond: Float = 0f
    override var totalOffensiveStrengthPerSecond: Float = 0f
    override var totalSurvivalFactor: Float = 0f
    override var totalHealth: Float = 0f



    override fun update(player: ICDPlayer) {
        this.entities = MManaWars.m.screen.getEntities { it is IControlled && it.team == player.team}.
                sortedByDescending { it.getDistanceHor(player.castle) }

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

        totalActionValue = 0f
        totalDefensiveStrengthPerSecond = 0f
        totalOffensiveStrengthPerSecond = 0f
        totalSurvivalFactor = 1f
        totalHealth = 0f
        this.entities.forEach {
            val e = it as IControlled
            val unitAnalysis = e.data.analysis
            totalActionValue += unitAnalysis.actionValue
            totalDefensiveStrengthPerSecond += unitAnalysis.defensiveStrengthPerSecond
            totalOffensiveStrengthPerSecond += unitAnalysis.offensiveStrengthPerSecond
            totalSurvivalFactor *= unitAnalysis.survivalFactor
            totalHealth += e.health
        }

    }
}