package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildingHeal
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.ITeamable

class CDEntityBuildingHeal(val data: IDataBuildingHeal) : CDEntityBuilding(data) {

    var lastHealing: Long = 0


    override fun doLogic(delta: Float) {
        super.doLogic(delta)
        val time = MManaWars.m.screen.getGameTime()
        if (lastHealing + data.cooldown <= time) {
            heal()
            lastHealing = time
        }
    }

    fun heal() {
        MManaWars.m.screen.getEntities { e ->
            e is ITeamable && e.team == this.team &&
                    e.getDistanceHor(this) < data.range &&
                    e is ILiving
        }.forEach { e ->
            (e as ILiving).heal(data.healingPower)
        }
    }
}