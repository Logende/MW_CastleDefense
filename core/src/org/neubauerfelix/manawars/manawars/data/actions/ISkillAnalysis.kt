package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.manawars.enums.MWSkillClass


/**
 * Computed once for every skill by the developer and later stored to/loaded from internal file.
 */
interface ISkillAnalysis : IDataActionProperties {

    val lifeTime: Float

    val width: Int
    val height: Int

    val collisionsPercentageHumanHead: Float
    val collisionsPercentageHumanBody: Float
    val collisionsPercentageMount: Float

    val skillClass: MWSkillClass


}