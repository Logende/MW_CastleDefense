package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.manawars.enums.MWArmorHolder
import org.neubauerfelix.manawars.manawars.enums.MWArmorType

class UnitAnalysisDummy : IUnitAnalysis {

    override val actionValue: Float = 1f
    override val survivalFactor: Float = 1f
    override val defensiveStrengthPerSecond: Float = 1f
    override val offensiveStrengthPerSecond: Float = 1f
    override val armor: Map<MWArmorHolder, MWArmorType> = hashMapOf()
}