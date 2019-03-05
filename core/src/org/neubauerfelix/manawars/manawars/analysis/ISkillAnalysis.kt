package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.manawars.enums.MWArmorHolder
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass


/**
 * Computed once for every skill by the developer and later stored to/loaded from internal file.
 */
interface ISkillAnalysis : IDataActionProperties {

    val lifeTime: Float

    val width: Int
    val height: Int

    val collisionsPercentages: Map<MWArmorHolder, Double>


    val skillClass: MWSkillClass


}