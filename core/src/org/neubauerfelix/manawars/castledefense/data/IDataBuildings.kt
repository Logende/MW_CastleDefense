package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.manawars.data.units.DataUnitUpgrade

interface IDataBuildings {

    val upgrades: List<DataUnitUpgrade> // upgrades which the upgrade buildings give to you
}