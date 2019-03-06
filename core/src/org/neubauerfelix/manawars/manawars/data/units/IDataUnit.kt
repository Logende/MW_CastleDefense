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
    val animation: IEntityAnimationProducer
    val armor: Map<MWArmorHolder, MWArmorType>
    val action: IDataAction
    val actionCooldown: Float

    val health: Float
    val stateMultipliers: Map<MWState, MWStateEffectivity>
    val skillMultipliers: Map<MWSkillClass, Float>
    val skillDurabilityMultipliers: Map<MWSkillClass, Float>
    val drainMultiplier: Float

    val walkSpeedMax: Float
    val walkAcceleration: Float

    val analysis: IUnitAnalysis


    fun produce(x: Float, y: Float, controller: IController, team: Int): IControlled
}