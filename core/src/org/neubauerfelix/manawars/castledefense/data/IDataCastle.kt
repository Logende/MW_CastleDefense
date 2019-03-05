package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.game.entities.ILocated

interface IDataCastle : IAsset {


    val textureName: String
    val unitSpawnOffset: ILocated


}