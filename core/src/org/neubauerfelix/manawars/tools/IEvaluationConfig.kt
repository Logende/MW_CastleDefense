package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.castledefense.CDMatchConfiguration

interface IEvaluationConfig {

    val matches: List<CDMatchConfiguration>
    val maxMatchLength: Long // a match will automatically abort if this number is reached
    val maxUnitsCount: Int // a match will automatically abort if the total unit count exceeds this value

    // TODO: more options like possibility of printing all events, allowing detailed plots
}