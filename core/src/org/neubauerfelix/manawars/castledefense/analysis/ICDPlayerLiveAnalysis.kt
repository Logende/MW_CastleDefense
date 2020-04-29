package org.neubauerfelix.manawars.castledefense.analysis

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.ILoadable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.events.IEvent
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

interface ICDPlayerLiveAnalysis : IDisposable, ILoadable {

    val entities: List<IEntity> // sorted by position: Furthest unit from tribe castle is first
    val units: LinkedHashMap<IDataUnit, Int> // unit and their count. Sorted by position: Furthest unit from tribe castle is first
    val skills: List<IEntity> // sorted by position: Furthest unit from tribe castle is first
    val furthestX: Float // x position of furthest unit or castle if no unit is existing +width if on left side

    val totalActionValue: Float // sum of action value of all units
    val totalDefensiveStrengthPerSecond: Float
    val totalOffensiveStrengthPerSecond: Float

    val totalSurvivalFactor: Float // product of survival factors of all units
    val totalHealth: Float // total unit health

    val recentEvents: List<IEvent> // list of recent events such as EntityDamageEvent

    fun update(player: ICDPlayer)
}