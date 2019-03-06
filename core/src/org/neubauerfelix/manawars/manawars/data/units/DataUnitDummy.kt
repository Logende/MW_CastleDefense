package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.analysis.IUnitAnalysis
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.controller.IController
import org.neubauerfelix.manawars.manawars.enums.*

class DataUnitDummy : IDataUnit {

    override val name: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val displayName: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val animation: IEntityAnimationProducer
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val armor: Map<MWArmorHolder, MWArmorType>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val action: IDataAction
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val actionCooldown: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val health: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val stateMultipliers: Map<MWState, MWStateEffectivity>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val skillMultipliers: Map<MWSkillClass, Float>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val skillDurabilityMultipliers: Map<MWSkillClass, Float>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val drainMultiplier: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val analysis: IUnitAnalysis
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val walkSpeedMax: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val walkAcceleration: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun produce(x: Float, y: Float, controller: IController, team: Int): IControlled {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAsset() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadedAsset() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disposeAsset() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}