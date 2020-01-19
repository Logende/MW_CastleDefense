package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.game.IComponent

abstract class DataTribe : IDataTribe {

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAsset() {
        castle.loadAsset()
        army.loadAsset()
    }

    override fun loadedAsset() {
        castle.loadedAsset()
        army.loadedAsset()
    }

    override fun disposeAsset() {
        castle.disposeAsset()
        army.disposeAsset()
    }
}