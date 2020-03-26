package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme

interface IDataTribe : IDataPresentable {

    val name: String
    val displayName: String

    val castle: IDataCastle // castle of tribe
    val army: IDataArmy // units of tribe

    val backgroundThemes: List<MWBackgroundTheme>
    val backgroundSubthemes: List<MWBackgroundSubtheme>
    //val league: IDataLeague <- Tribe should not be coupled to league

}