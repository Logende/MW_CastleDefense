package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.analysis.IUnitAnalysis
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.*

interface IDataUnit : IDataPresentable, IAsset {

    val name: String
    val displayName: String
    val unitType: MWUnitType
    val unitRarity: MWUnitRarity
    val animation: IEntityAnimationProducer
    val armor: MWArmorType
    val action: IDataAction
    var actionCooldown: Float

    var health: Float
    val stateMultipliers: MutableMap<MWState, MWStateEffectivity>
    val skillMultipliers: MutableMap<MWSkillClass, Float>
    val skillDurabilityMultipliers: MutableMap<MWSkillClass, Float>
    var drainMultiplier: Float

    var walkSpeedMax: Float
    var walkAcceleration: Float

    val cost: Int

    val analysis: IUnitAnalysis


    fun produce(x: Float, y: Float, controller: IController, team: Int): IControlled
}