package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.DataUnitLoaded
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.animation.human.EntityAnimationProducerHuman
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataArmyLoaded(config: Configuration) : DataArmy() {

    override val name: String = config.getString("name")
    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("unit_${name}_name")
    override val units: List<IDataUnit>

    init {
        val units = ArrayList<IDataUnit>()
        for (unit in config.getSection("units").keys) {
            val unitSection = config.getSection("units").getSection(unit)
            units.add(DataUnitLoaded("$name.$unit", unitSection))
        }
        this.units = units.sortedBy {
            val animation = it.animation
            val rangeFactor = it.action.getActionProperties(animation.animationType).rangeMaxAvg
            val shieldFactor = if (animation is EntityAnimationProducerHuman && animation.shield != null) {
                1f / animation.shield.textureRegion.regionHeight
            } else {
                1f
            }
            // TODO: Include armor?
            rangeFactor * shieldFactor
        }
    }

    override val castle: IDataCastle = DataCastleLoaded(config.getSection("castle"))
}