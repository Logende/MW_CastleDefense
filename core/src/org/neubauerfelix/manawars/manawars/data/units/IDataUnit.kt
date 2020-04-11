package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.*

interface IDataUnit : IDataPresentable, IDataActionUser {

    val name: String
    val displayName: String
    val rarity: NWRarity
    val animation: IEntityAnimationProducer
    val armor: MWArmorType

    var health: Float
    val stateMultipliers: MutableMap<MWState, MWStateEffectivity>
    val skillMultipliers: MutableMap<MWSkillClass, Float>
    val skillDurabilityMultipliers: MutableMap<MWSkillClass, Float>
    var drainMultiplier: Float

    var walkSpeedMax: Float
    var walkAcceleration: Float

    val cost: Int



    fun produce(x: Float, y: Float, controller: IController, team: Int): IControlled
}