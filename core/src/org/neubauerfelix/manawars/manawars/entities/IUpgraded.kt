package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.enums.MWArmorType
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass


interface IUpgraded: IActionUser, ILiving {

    fun getSkillMultiplier(skillClass: MWSkillClass): Float
    fun getDurabilityMultiplier(skillClass: MWSkillClass): Float
    fun getArmor(collisionType: MWCollisionType): MWArmorType
    val drainMultiplier: Float

}