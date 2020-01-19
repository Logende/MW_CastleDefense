package org.neubauerfelix.manawars.castledefense.data.tribes


abstract class DataArmy : IDataArmy {


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