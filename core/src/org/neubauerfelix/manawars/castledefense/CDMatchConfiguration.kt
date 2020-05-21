package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe

data class CDMatchConfiguration(
        val controllerA: CDControllerType,
        val controllerB: CDControllerType,
        val tribeA: IDataTribe,
        val tribeB: IDataTribe
// TODO: Add playground?
)