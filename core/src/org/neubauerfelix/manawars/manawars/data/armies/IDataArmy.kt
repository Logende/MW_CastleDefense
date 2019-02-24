package org.neubauerfelix.manawars.manawars.data.armies

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

interface IDataArmy : IDataPresentable, IAsset {

    val name: String
    val displayName: String
    val units: List<IDataUnit>
}