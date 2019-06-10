package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.castledefense.data.IDataLeague

interface ILeagueHandler: IHandler {


    fun putLeague(league: IDataLeague)
    fun getLeague(name: String): IDataLeague?
    fun listLeagues(): Iterable<IDataLeague>


}