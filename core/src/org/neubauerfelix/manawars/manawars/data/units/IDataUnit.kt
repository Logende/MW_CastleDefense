package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable

interface IDataUnit : IDataPresentable, IAsset {

    val name: String
    val displayName: String
    val animation: IDataUnitAnimation
    val action: IDataAction
}