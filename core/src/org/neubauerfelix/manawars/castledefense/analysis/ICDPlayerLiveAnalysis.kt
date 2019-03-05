package org.neubauerfelix.manawars.castledefense.analysis

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.entities.IEntity

interface ICDPlayerLiveAnalysis {

    val units: List<IEntity> // sorted by position: Furthest unit from player castle is first
    val skills: List<IEntity> // sorted by position: Furthest unit from player castle is first
    val furthestX: Float // x position of furthest unit or castle if no unit is existing +width if on left side

    val totalActionValue: Float // sum of action value of all units
    val totalDefensiveStrengthPerSecond: Float
    val totalOffensiveStrengthPerSecond: Float

    val totalSurvivalFactor: Float // product of survival factors of all units
    val totalHealth: Float // total unit health

    fun update(player: ICDPlayer)
}