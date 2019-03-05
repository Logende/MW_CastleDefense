package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.game.IComponent

abstract class DataArmy : IDataArmy {


    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAsset() {
        units.forEach { unit -> unit.loadAsset() }
        castle.loadAsset()
    }

    override fun loadedAsset() {
        units.forEach { unit -> unit.loadedAsset() }
        castle.loadedAsset()
    }

    override fun disposeAsset() {
        units.forEach { unit -> unit.disposeAsset() }
        castle.disposeAsset()
    }


}