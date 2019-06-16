package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.manawars.enums.MWState

interface IDataSkillEffect {


    // attack action
    val skillStrength: Int
    val damageMin: Int
    val damageMax: Int
    val stateEffect: MWState?
    val stateEffectDuration: Float
    val knockbackFactor: Float
    val spawnOnImpact: String? // spawns certain action on impact with enemy
}