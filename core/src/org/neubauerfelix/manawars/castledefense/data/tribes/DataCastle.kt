package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.manawars.MManaWars


abstract class DataCastle : IDataCastle {

    override fun loadAsset() {
        MManaWars.m.getAssetLoader().loadTexture(textureName)
    }

    override fun loadedAsset() {
    }

    override fun disposeAsset() {
        MManaWars.m.getAssetLoader().unloadAsset(textureName)
    }
}