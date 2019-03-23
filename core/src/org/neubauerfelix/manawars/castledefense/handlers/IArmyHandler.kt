package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.castledefense.data.IDataArmy

interface IArmyHandler: IHandler {


    fun putArmy(army: IDataArmy)
    fun getArmy(name: String): IDataArmy?
    fun listArmies(): Iterable<IDataArmy>


}