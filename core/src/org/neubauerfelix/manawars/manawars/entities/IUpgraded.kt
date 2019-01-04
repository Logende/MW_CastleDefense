package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.enums.MWSkillClass


interface IUpgraded: IActionUser, ILiving {

    fun getSkillMultiplier(skillClass: MWSkillClass): Float
    fun getDurabilityMultiplier(skillClass: MWSkillClass): Float
    val drainMultiplier: Float

}