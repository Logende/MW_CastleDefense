package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable

interface IDataTribe : IDataPresentable {

    val name: String
    val displayName: String

    val castle: IDataCastle // castle of tribe
    val army: IDataArmy // units of tribe
    val league: IDataLeague

}