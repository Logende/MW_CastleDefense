package org.neubauerfelix.manawars.castledefense.ki.traditional

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.ki.BaseFeatures
import org.neubauerfelix.manawars.castledefense.ki.CDKIFeaturePreparation
import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.ICDKI
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.DataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitType
import org.neubauerfelix.manawars.manawars.handlers.TextVisualizationHandler

/**
 * Naive ki which builds just units and chooses units by their effectiveness value of the current tick
 */
class CDKITraditionalFelix() : ICDKI {

    enum class SITUATION_TYPE {
        ATTACKING_STRONG,
        ATTACKING_NEED_SUPPORT,
        ATTACKING_LOST,
        IDLE,
        DEFENDING_FORTRESS
    }

    // TODO: Safe some money for buildings

    override fun compute(player: ICDPlayer) : CDKILabel {
        val prep = CDKIFeaturePreparation()
        prep.prepare(player)

        val situation = getSituationType(player, prep)

        CDManaWars.cd.getHandler(TextVisualizationHandler::class.java).displayText(player.castle,
                player.castle.centerHorizontal, player.castle.top, situation.name, "situation", 20)
        return when (situation) {
            SITUATION_TYPE.ATTACKING_LOST, SITUATION_TYPE.IDLE -> actWithStrategy(player, prep)
            SITUATION_TYPE.ATTACKING_NEED_SUPPORT, SITUATION_TYPE.ATTACKING_STRONG -> actSupportive(player, prep)
            SITUATION_TYPE.DEFENDING_FORTRESS -> actDefensive(player, prep)
        }

    }

    private fun getSituationType(player: ICDPlayer, prep: CDKIFeaturePreparation) : SITUATION_TYPE {
        val unitsPlayer = BaseFeatures.countUnitTotal(player)
        val unitsEnemy = BaseFeatures.countUnitTotal(player.enemy)

        val distanceEnemyToCastle = BaseFeatures.distancePlayerToEnemyCastle(player.enemy)
        if (distanceEnemyToCastle <= 1500.0 || prep.castleTookDamage) {
            return SITUATION_TYPE.DEFENDING_FORTRESS
        }


        if (unitsPlayer == 0.0 && distanceEnemyToCastle > 1500) {
            return SITUATION_TYPE.IDLE
        }

        val unitCountBoss = BaseFeatures.countUnit1(player)
        val unitCountTank = BaseFeatures.countUnit2(player)
        val unitCountMelee = BaseFeatures.countUnit3(player)

        if (prep.damageDealt > prep.damageTaken * 1.5 &&
                (unitCountBoss >= 1.0 || unitCountTank>=2.0 || unitCountMelee>=3.0)) {
            return SITUATION_TYPE.ATTACKING_STRONG
        }


        if (prep.damageTaken > prep.damageDealt * 1.2 &&
                (unitCountBoss == 0.0 && unitCountTank<=1.0 && unitCountMelee<=2.0)
                && unitsEnemy > unitsPlayer
                && distanceEnemyToCastle > 2400) {
            return SITUATION_TYPE.ATTACKING_LOST
        }

        return SITUATION_TYPE.ATTACKING_NEED_SUPPORT
    }




    fun actWithStrategy(player: ICDPlayer, prep: CDKIFeaturePreparation) : CDKILabel {
        // TODO: Plan a strong attack group and safe until it can be built. Then build it
        return CDKILabel.NONE // TODO
    }

    fun actDefensive(player: ICDPlayer, prep: CDKIFeaturePreparation) : CDKILabel {
        val unitCountBoss = BaseFeatures.countUnit1(player)
        val unitCountTank = BaseFeatures.countUnit2(player)
        val unitCountMelee = BaseFeatures.countUnit3(player)
        val unitCountRanger = BaseFeatures.countUnit4(player)
        val unitCountMage = BaseFeatures.countUnit5(player)
        val effectivenessTank = BaseFeatures.effectivenessUnit2(player, prep)
        val effectivenessMelee = BaseFeatures.effectivenessUnit3(player, prep)
        val effectivenessRanger = BaseFeatures.effectivenessUnit4(player, prep)
        val effectivenessMage = BaseFeatures.effectivenessUnit5(player, prep)
        val distanceEnemyToCastle = BaseFeatures.distancePlayerToEnemyCastle(player.enemy)

        val rangeTank = BaseFeatures.unit2(player).action.rangeMax
        val rangeMelee = BaseFeatures.unit3(player).action.rangeMax
        val rangeRanger = BaseFeatures.unit4(player).action.rangeMax
        val rangeMage = BaseFeatures.unit5(player).action.rangeMax

        // if range is big enough: make sure defensive units are there
        if (rangeTank > distanceEnemyToCastle || rangeMelee > distanceEnemyToCastle) {
            val defensePoints = unitCountBoss * 6 + unitCountTank * 3 + unitCountMelee * 1
            if (defensePoints <= 1) {

                return if (rangeTank > distanceEnemyToCastle) {
                    CDKILabel.UNIT_TANK
                } else {
                    CDKILabel.UNIT_MELEE
                }
            }
        }

        // other than that, build units with high attack value
        return if (effectivenessMage >= effectivenessRanger &&
                (unitCountMage <= 3 ||  unitCountRanger >= unitCountMage / 2.0)) {
            CDKILabel.UNIT_MAGE
        } else {
            CDKILabel.UNIT_RANGER
        }

    }

    fun actSupportive(player: ICDPlayer, prep: CDKIFeaturePreparation) : CDKILabel {
        val unitCountBoss = BaseFeatures.countUnit1(player)
        val unitCountTank = BaseFeatures.countUnit2(player)
        val unitCountMelee = BaseFeatures.countUnit3(player)
        val unitCountRanger = BaseFeatures.countUnit4(player)
        val unitCountMage = BaseFeatures.countUnit5(player)
        val effectivenessTank = BaseFeatures.effectivenessUnit2(player, prep)
        val effectivenessMelee = BaseFeatures.effectivenessUnit3(player, prep)
        val effectivenessRanger = BaseFeatures.effectivenessUnit4(player, prep)
        val effectivenessMage = BaseFeatures.effectivenessUnit5(player, prep)

        val defensePoints = unitCountBoss * 6 + unitCountTank * 3 + unitCountMelee * 1
        if (defensePoints <= 4) {

            return if (effectivenessTank > effectivenessMelee || unitCountMelee * 0.8 > unitCountTank) {
                CDKILabel.UNIT_TANK
            } else {
                CDKILabel.UNIT_MELEE
            }
        }

        if (effectivenessMelee > effectivenessRanger && effectivenessMelee > effectivenessMage &&
                unitCountMelee <= 2) {
            return CDKILabel.UNIT_MELEE
        }

        return if (effectivenessMage >= effectivenessRanger &&
                (unitCountMage <= 5 ||  unitCountRanger >= unitCountMage / 2.0)) {
            CDKILabel.UNIT_MAGE
        } else {
            CDKILabel.UNIT_RANGER
        }

    }



}