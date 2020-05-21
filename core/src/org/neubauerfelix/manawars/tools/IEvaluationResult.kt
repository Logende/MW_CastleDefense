package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.castledefense.CDMatchConfiguration


interface IEvaluationResult {

    enum class VictoryType {
        PLAYER_A,
        PLAYER_B,
        TIMEOUT,
        TOO_MANY_UNITS
    }

    val match: CDMatchConfiguration
    val victoryType: VictoryType
    val matchDuration: Long

}