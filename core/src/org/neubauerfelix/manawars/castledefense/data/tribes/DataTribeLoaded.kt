package org.neubauerfelix.manawars.castledefense.data.tribes

import org.neubauerfelix.manawars.castledefense.data.ICastleProvider
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataTribeLoaded(config: Configuration, castleProvider: ICastleProvider) : DataTribe() {

    override val name: String = config.getString("name")
    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("unit_${name}_name")

    init {
        println("Loading tribe $name.")
    }

    override val army: IDataArmy = DataArmyLoaded(config.getSection("army"), this.name, this)
    override val castle: IDataCastle = castleProvider.getCastle(config.getString("castle"))!!







}