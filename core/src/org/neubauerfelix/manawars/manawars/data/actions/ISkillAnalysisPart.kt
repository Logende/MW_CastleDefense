package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType


/**
 * Computed once for every skill and every combination of animation types by the developer and later stored to/loaded from internal file.
 */
interface ISkillAnalysisPart {

    val attackerAnimationType : MWEntityAnimationType
    val targetAnimationType : MWEntityAnimationType

    val tacticalDamage: Float
    val hitProbability: Float
    val tacticalStrength: Float

    val collisionsPercentageHumanHead: Float
    val collisionsPercentageHumanBody: Float
    val collisionsPercentageMount: Float

    val lifeTime: Float
    val rangeMax: Int
    val rangeMin: Int
    val width: Int
    val height: Int

}