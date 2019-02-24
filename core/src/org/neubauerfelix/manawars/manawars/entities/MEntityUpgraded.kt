package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.*

open class MEntityUpgraded(animationProducer: IEntityAnimationProducer,
                           health: Float,
                           action: IDataAction,
                           actionCooldown: Long,
                           stateMultipliers: Map<MWState, MWStateEffectivity> = HashMap(),
                           val skillMultipliers: Map<MWSkillClass, Float> = HashMap(),
                           val skillDurabilityMultipliers: Map<MWSkillClass, Float> = HashMap(),
                           final override val drainMultiplier: Float = 0f,
                           val armor: Map<MWCollisionType, MWArmorType> = HashMap()):
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
        return if (armor.containsKey(collisionType)) {
            armor[collisionType]!!
        } else {
            MWArmorType.NONE
        }
    }
}