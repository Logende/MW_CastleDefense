package org.neubauerfelix.manawars.manawars.data.actions


/**
 * Computed once for every skill by the developer and later stored to/loaded from internal file.
 */
interface ISkillAnalysis : IDataActionProperties {

    val lifeTime: Float

    val width: Int
    val height: Int


    val tacticalDamage: Float
    val hitProbability: Float
    val tacticalStrength: Float

    val collisionsPercentageHumanHead: Float
    val collisionsPercentageHumanBody: Float
    val collisionsPercentageMount: Float


}