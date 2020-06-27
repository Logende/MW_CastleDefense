package org.neubauerfelix.manawars.castledefense.ki.traditional

import org.neubauerfelix.manawars.castledefense.ki.CDKIFeaturePreparation
import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.ICDKI
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitType

/**
 * Smart KI which saves gold when not attacked and then launches strong attacks
 */
class CDKITraditionalRPS() : ICDKI {


    override fun compute(player: ICDPlayer) : CDKILabel {
        val prep = CDKIFeaturePreparation()
        prep.prepare(player)

        val mostEnemiesType = prep.enemyUnitDistribution.maxBy { it.value }!!.key
        return when (mostEnemiesType.unitType) {
            MWUnitType.KNIGHT -> return CDKILabel.UNIT_MAGE
            MWUnitType.ARCHER -> return CDKILabel.UNIT_KNIGHT
            MWUnitType.MAGE -> return CDKILabel.UNIT_ARCHER
            else -> CDKILabel.values().random()
        }

    }

    override fun getUnitsToBuildNextCycle(player: ICDPlayer): List<IDataUnit> {
        val prep = CDKIFeaturePreparation()
        prep.prepare(player)

        val unitsForNextCycle = arrayListOf<IDataUnit>()

        var moneyLeft = player.castle.moneyPerCycle // TODO: later only use some of the money
        val units = player.tribe.army.units
        val cheapestUnitCost = units.map { it.cost }.min()!!
        val mostEnemiesType = prep.enemyUnitDistribution.maxBy { it.value }!!.key
        when (mostEnemiesType.unitType) {
            MWUnitType.KNIGHT -> {
                val unit = units[MWUnitType.MAGE.index]
                val unitCount = moneyLeft / unit.cost
                for (i in 0 .. (unitCount)) {
                    unitsForNextCycle.add(unit)
                    moneyLeft -= unit.cost
                }
            }
            MWUnitType.ARCHER ->  {
                val unit = units[MWUnitType.KNIGHT.index]
                val unitCount = moneyLeft / unit.cost
                for (i in 0 .. (unitCount)) {
                    unitsForNextCycle.add(unit)
                    moneyLeft -= unit.cost
                }
            }
            MWUnitType.MAGE ->  {
                val unit = units[MWUnitType.ARCHER.index]
                val unitCount = moneyLeft / unit.cost
                for (i in 0 .. (unitCount)) {
                    unitsForNextCycle.add(unit)
                    moneyLeft -= unit.cost
                }
            }
            else -> {}
        }

        while (moneyLeft >= cheapestUnitCost) {
            val unit = units.minBy { it.cost }!!
            unitsForNextCycle.add(unit)
            moneyLeft -= unit.cost
        }

        return unitsForNextCycle
    }
}