package org.neubauerfelix.manawars.castledefense.ki.traditional

import org.neubauerfelix.manawars.castledefense.ki.CDKIFeaturePreparation
import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.ICDKI
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitType

/**
 * Naive ki which builds just units and chooses units by their effectiveness value of the current tick
 */
class CDKITraditionalNaive() : ICDKI {

    override fun getUnitsToBuildNextCycle(player: ICDPlayer): List<IDataUnit> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun compute(player: ICDPlayer) : CDKILabel {
        val prep = CDKIFeaturePreparation()
        prep.prepare(player)

        val maxEffectiveness = prep.playerUnitEffectiveness.values.max()
        val options = prep.playerUnitEffectiveness.filter { it.value == maxEffectiveness }
        val pick = options.keys.random()

        return when(pick.unitType) {
            MWUnitType.BOSS -> CDKILabel.UNIT_BOSS
            MWUnitType.KNIGHT -> CDKILabel.UNIT_KNIGHT
            MWUnitType.MELEE -> CDKILabel.UNIT_MELEE
            MWUnitType.ARCHER -> CDKILabel.UNIT_ARCHER
            MWUnitType.MAGE -> CDKILabel.UNIT_MAGE
            else -> CDKILabel.NONE
        }

    }

}