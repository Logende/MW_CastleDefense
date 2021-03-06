package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.castledefense.data.IDataPlayground
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.manawars.data.actions.IDataPresentable
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme

interface IDataTribe : IDataPresentable {

    val name: String
    val displayName: String

    val castle: IDataCastle // castle of tribe
    val army: IDataArmy // units of tribe

    val backgroundTheme: MWBackgroundTheme
    val backgroundSubthemes: List<MWBackgroundSubtheme>
    val musicTrack: String

    val playground: IDataPlayground
    val buildings: List<IDataBuilding>

    companion object {
        fun findTribe(tribes: Iterable<IDataTribe>, unit: IDataUnit) : IDataTribe {
            for (tribe in tribes) {
                if (tribe.army.units.contains(unit)) {
                    return tribe
                }
            }
            error("Unit ${unit.name} does not belong to any tribe")
        }
    }

}