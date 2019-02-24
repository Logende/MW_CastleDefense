package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

interface IDataActionProperties {


    val strategicValue: Float
    val successProbability: Float
    val defensivePoints: Float
    val offensivePoints: Float


    val rangeMax: Map<MWEntityAnimationType, Int>
    val rangeMin: Map<MWEntityAnimationType, Int>

}