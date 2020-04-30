package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.*
import java.util.*

open class MEntityUpgraded(animationProducer: IEntityAnimationProducer,
                           health: Float,
                           action: IDataAction,
                           actionCooldown: Float,
                           stateMultipliers: Map<MWState, MWStateEffectivity> = EnumMap(MWState::class.java),
                           val skillMultipliers: Map<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java),
                           val skillDurabilityMultipliers: Map<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java),
                           final override val drainMultiplier: Float = 0f,
                           val armor: MWArmorType = MWArmorType.NONE):
        MEntityStateable(animationProducer, health, action, actionCooldown, stateMultipliers), IUpgraded {


    init {
        require(drainMultiplier >= 0)
    }


    override fun getSkillMultiplier(skillClass: MWSkillClass): Float {
        return if (skillMultipliers.containsKey(skillClass)) {
            skillMultipliers[skillClass]!!
        } else {
            1f
        }
    }

    override fun getDurabilityMultiplier(skillClass: MWSkillClass): Float {
        return if (skillDurabilityMultipliers.containsKey(skillClass)) {
            skillDurabilityMultipliers[skillClass]!!
        } else {
            1f
        }
    }

    override fun getArmor(collisionType: MWCollisionType): MWArmorType {
        return armor
    }

}