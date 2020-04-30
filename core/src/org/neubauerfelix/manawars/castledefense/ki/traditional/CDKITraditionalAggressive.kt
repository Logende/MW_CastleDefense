package org.neubauerfelix.manawars.castledefense.ki.traditional

import org.neubauerfelix.manawars.castledefense.ki.BaseFeatures
import org.neubauerfelix.manawars.castledefense.ki.CDKIFeaturePreparation
import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.ICDKI
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import kotlin.math.max
import kotlin.math.min

/**
 * Aggressive KI which keeps building units and tries to keep up a healthy balance of defense and offense
 */
class CDKITraditionalAggressive() : ICDKI {


    override fun compute(player: ICDPlayer) : CDKILabel {
        val prep = CDKIFeaturePreparation()
        prep.prepare(player)

        val unitCountBoss = BaseFeatures.countUnit1(player)
        val unitCountTank = BaseFeatures.countUnit2(player)
        val unitCountMelee = BaseFeatures.countUnit3(player)
        val unitCountRanger = BaseFeatures.countUnit4(player)
        val unitCountMage = BaseFeatures.countUnit5(player)
        val unitCount = BaseFeatures.countUnitTotal(player)
        val effectivenessTank = BaseFeatures.effectivenessUnit2(player, prep)
        val effectivenessMelee = BaseFeatures.effectivenessUnit3(player, prep)
        val effectivenessRanger = BaseFeatures.effectivenessUnit4(player, prep)
        val effectivenessMage = BaseFeatures.effectivenessUnit5(player, prep)

        val defensePoints = unitCountBoss * 2.5 + unitCountTank * 1.0 + unitCountMelee * 0.5
        // at least 1.5. Not more than 5. Ideally 1/3 of team
        val neededDefensePoints = max(1.5, min(5.0, unitCount / 3.0 ))
        if (defensePoints < neededDefensePoints) {
            return chooseBetterPick(CDKILabel.UNIT_TANK, CDKILabel.UNIT_MELEE, effectivenessTank, effectivenessMelee,
                    unitCountTank, unitCountMelee)
        }

        if (effectivenessMelee > effectivenessRanger && effectivenessMelee > effectivenessMage &&
                unitCountMelee <= 2) {
            return CDKILabel.UNIT_MELEE
        }

        return chooseBetterPick(CDKILabel.UNIT_MAGE, CDKILabel.UNIT_RANGER, effectivenessMage, effectivenessRanger,
                unitCountMage, unitCountRanger)

    }

    private fun chooseBetterPick(a: CDKILabel, b: CDKILabel, effectivenessA: Double, effectivenessB: Double,
                                 unitCountA: Double, unitCountB: Double) : CDKILabel {
        val ratioA = effectivenessA / effectivenessB // example: effA = 0.4 and effB = 0.2 -> ratio = 2
        return if (unitCountA * ratioA > unitCountB) {
            b
        } else {
            a
        }
    }



}