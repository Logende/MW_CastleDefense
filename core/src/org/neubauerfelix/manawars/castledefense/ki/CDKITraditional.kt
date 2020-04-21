package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer

class CDKITraditional() : ICDKI {

    override fun compute(player: ICDPlayer) : CDKILabel {



        return CDKILabel.UNIT_MAGE
    }

}