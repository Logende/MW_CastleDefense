package org.neubauerfelix.manawars.manawars.data.armies

import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.controller.IController

abstract class DataArmy : IDataArmy {


    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadAsset() {
        units.forEach { unit -> unit.loadAsset() }
    }

    override fun loadedAsset() {
        units.forEach { unit -> unit.loadedAsset() }
    }

    override fun disposeAsset() {
        units.forEach { unit -> unit.disposeAsset() }
    }


}