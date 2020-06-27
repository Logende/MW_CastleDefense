package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

interface ICDKI {

    fun compute(player: ICDPlayer) : CDKILabel // TODO: new labels for mana actions
    fun getUnitsToBuildNextCycle(player: ICDPlayer): List<IDataUnit>
}