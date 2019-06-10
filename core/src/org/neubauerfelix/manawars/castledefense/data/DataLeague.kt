package org.neubauerfelix.manawars.castledefense.data


abstract class DataLeague : IDataLeague {


    override fun loadAsset() {
        tribes.forEach { it.loadAsset() }
    }

    override fun loadedAsset() {
        tribes.forEach { it.loadedAsset() }
    }

    override fun disposeAsset() {
        tribes.forEach { it.disposeAsset() }
    }


}