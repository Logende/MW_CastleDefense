package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable

interface IDataTribe : IAsset, IDataPresentable {

    val name: String
    val displayName: String

    val castle: IDataCastle // castle of tribe
    val army: IDataArmy // units of tribe
    val league: IDataLeague

}