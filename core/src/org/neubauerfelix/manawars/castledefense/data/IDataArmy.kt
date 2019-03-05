package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

interface IDataArmy : IDataPresentable, IAsset {

    val name: String
    val displayName: String
    val units: List<IDataUnit> // units sorted by formation: for example shield guys should be first and long range entities last
    val castle: IDataCastle
}