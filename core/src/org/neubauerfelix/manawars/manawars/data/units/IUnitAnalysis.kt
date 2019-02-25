package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.manawars.enums.MWArmorHolder
import org.neubauerfelix.manawars.manawars.enums.MWArmorType

interface IUnitAnalysis {


    // strategic value of action * success probability of action / action cooldown
    val actionValue: Float

    // 1.0 = average survival factor. More health, more range and armor result in a higher factor, while
    // a low range and low health result in a low factor
    val survivalFactor: Float

    val defensiveStrengthPerSecond: Float
    val offensiveStrengthPerSecond: Float

    val armor: Map<MWArmorHolder, MWArmorType>

}