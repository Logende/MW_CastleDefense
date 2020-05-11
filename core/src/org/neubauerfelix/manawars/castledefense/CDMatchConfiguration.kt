package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.castledefense.player.ICDController

data class CDMatchConfiguration(
        val controllerA: ICDController,
        val controllerB: ICDController,
        val tribeA: IDataTribe,
        val tribeB: IDataTribe
)