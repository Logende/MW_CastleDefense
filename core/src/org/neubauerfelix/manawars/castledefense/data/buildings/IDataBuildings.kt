package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.data.units.DataUnitUpgrade

interface IDataBuildings : IAsset{

    val buildings: List<IDataBuilding>
    val upgrades: List<DataUnitUpgrade> // upgrades which the upgrade buildings give to you
}