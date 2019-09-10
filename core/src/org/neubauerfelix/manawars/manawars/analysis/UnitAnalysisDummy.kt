package org.neubauerfelix.manawars.manawars.analysis

import org.neubauerfelix.manawars.manawars.enums.MWArmorType

class UnitAnalysisDummy : AUnitAnalysis() {

    override val actionValue: Float = 1f
    override val survivalFactor: Float = 1f
    override val defensiveStrengthPerSecond: Float = 1f
    override val offensiveStrengthPerSecond: Float = 1f
    override val cost: Int = 0
    override val armor: MWArmorType = MWArmorType.NONE


}