package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.castledefense.CDMatchConfiguration

open class EvaluationResult : IEvaluationResult {

    override lateinit var match: CDMatchConfiguration
    override lateinit var victoryType: IEvaluationResult.VictoryType
    override var matchDuration: Long = -1
}