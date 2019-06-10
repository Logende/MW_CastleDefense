package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataTribeLoaded(config: Configuration, league: IDataLeague) : DataTribe() {

    override val name: String = config.getString("name")
    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("unit_${name}_name")

    init {
        println("Loading tribe $name.")
    }

    override val army: IDataArmy = DataArmyLoaded(config.getSection("army"), this.name, league)
    override val castle: IDataCastle = league.getCastle(config.getString("castle"))!!







}