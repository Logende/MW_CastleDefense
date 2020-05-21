package org.neubauerfelix.manawars.castledefense.profile

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataArmy
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitType
import org.neubauerfelix.manawars.manawars.storage.Configuration

class PlayerArmy(config: Configuration, override val tribe: IDataTribe) : IDataArmy {

    override val units = mutableListOf<IDataUnit>()
    val unitTribes = mutableListOf<IDataTribe>()

    fun replaceUnit(unitType: MWUnitType, tribe: IDataTribe) {
        val index = unitType.index
        units[index] = tribe.army.units[index]
        unitTribes[index] = tribe
    }

    init {
        val tribeHandler = CDManaWars.cd.getTribeHandler()
        for (unitType in MWUnitType.values()) {
            if (unitType.main) {
                val index = unitType.index
                val tribeName = config.getString("unit.$index", CDConstants.STARTER_TRIBE)
                val tribe = tribeHandler.getTribe(tribeName)!!
                val unit = tribe.army.units[index]
                units.add(unit)
                unitTribes.add(tribe)
            }
        }
    }


}