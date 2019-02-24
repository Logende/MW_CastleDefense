package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.game.data.IAsset
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
    val armor: Map<MWCollisionType, MWArmorType>
    val action: IDataAction
    val actionCooldown: Long

    val health: Float
    val stateMultipliers: Map<MWState, MWStateEffectivity>
    val skillMultipliers: Map<MWSkillClass, Float>
    val skillDurabilityMultipliers: Map<MWSkillClass, Float>
    val drainMultiplier: Float


    fun produce(x: Float, y: Float, controller: IController, team: Int): IControlled
}