package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer

interface ICDKI {
    fun compute(player: ICDPlayer) : CDKILabel
}