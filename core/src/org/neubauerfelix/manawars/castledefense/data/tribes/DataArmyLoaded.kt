package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.manawars.data.units.DataUnitLoaded
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitType
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataArmyLoaded(config: Configuration, playerName: String, override val tribe: IDataTribe) : DataArmy() {

    override val units: List<IDataUnit>



    init {
        val units = ArrayList<IDataUnit>()
        val unitsSection = config.getSection("units")
        for (unitType in MWUnitType.values()) {
            if (unitsSection.contains(unitType.name.toLowerCase())) {
                val unitSection = unitsSection.getSection(unitType.name.toLowerCase())
                val name = "$playerName.${unitType.name.toLowerCase()}"
                units.add(DataUnitLoaded(name, unitSection, this, unitType))
            }
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