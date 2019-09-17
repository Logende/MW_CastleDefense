package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

interface IDataActionProperties {


    // strategic value in case of success. 1 damage to an enemy equals a strategic value of 1
    val strategicValue: Float

    // 1 skill strength equals one strength. Attacking skills increase both offensive and defensive strength.
    val defensiveStrength: Float
    val offensiveStrength: Float


    val rangeMax: Map<MWEntityAnimationType, Int>
    val rangeMin: Map<MWEntityAnimationType, Int>
    val rangeMaxAvg: Float


}