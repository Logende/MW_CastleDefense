package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass
import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.enums.MWStateEffectivity

open class MEntityControlled(animationProducer: IEntityAnimationProducer,
                             health: Float, mana: Float,
                             actions: Array<IDataAction>,
                             manaRegen: Float,
                             stateMultipliers: Map<MWState, MWStateEffectivity> = HashMap(),
                             skillMultipliers: Map<MWSkillClass, Float> = HashMap(),
                             skillDurabilityMultipliers: Map<MWSkillClass, Float> = HashMap(),
                             drainMultiplier: Float = 0f):
        MEntityUpgraded(animationProducer, health, mana, actions, manaRegen, stateMultipliers, skillMultipliers, skillDurabilityMultipliers, drainMultiplier) {




}