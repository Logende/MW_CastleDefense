package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.manawars.data.units.DataUnitLoaded
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataArmyLoaded(config: Configuration, playerName: String, override val tribe: IDataTribe) : DataArmy() {

    override val units: List<IDataUnit>



    init {
        val units = ArrayList<IDataUnit>()
        for (unit in config.getSection("units").keys) {
            val unitSection = config.getSection("units").getSection(unit)
            units.add(DataUnitLoaded("$playerName.$unit", unitSection, this))
        }
        this.units = units /*.sortedBy {
            val animation = it.animation
            val rangeFactor = it.action.getActionProperties(animation.animationType).rangeMaxAvg
            val shieldFactor = if (animation is EntityAnimationProducerHuman && animation.shield != null) {
                1f / animation.shield.textureRegion.regionHeight
            } else {
                1f
            }
            // TODO: Include armor?
            rangeFactor * shieldFactor
        }*/

    }

}