package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass
import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.enums.MWStateEffectivity

open class MEntityUpgraded(animationProducer: IEntityAnimationProducer,
                      health: Float, mana: Float,
                      actions: Array<IDataAction>,
                      manaRegen: Float,
                      stateMultipliers: Map<MWState, MWStateEffectivity> = HashMap(),
                      val skillMultipliers: Map<MWSkillClass, Float> = HashMap(),
                      val skillDurabilityMultipliers: Map<MWSkillClass, Float> = HashMap(),
                      override val drainMultiplier: Float = 0f):
        MEntityStateable(animationProducer, health, mana, actions, manaRegen, stateMultipliers), IUpgraded {


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

}