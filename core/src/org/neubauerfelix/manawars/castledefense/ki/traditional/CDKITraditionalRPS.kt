package org.neubauerfelix.manawars.castledefense.ki.traditional

import org.neubauerfelix.manawars.castledefense.ki.CDKIFeaturePreparation
import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.ICDKI
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
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



}