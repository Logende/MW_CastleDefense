package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.castledefense.data.IDataPlayground
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.IDataBackground

interface IPlaygroundListHandler: IHandler {


    val playgrounds: Map<String, IDataPlayground>


}