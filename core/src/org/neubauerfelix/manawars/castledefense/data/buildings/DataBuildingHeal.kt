package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer

class DataBuildingHeal : IDataBuilding {

    override val health: Float
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val animationProducer: IEntityAnimationProducer
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun produce(x: Float, y: Float, team: Int): ILiving {
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