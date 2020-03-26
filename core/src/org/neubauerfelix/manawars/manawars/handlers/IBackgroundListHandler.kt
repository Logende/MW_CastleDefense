package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.IDataBackground

interface IBackgroundListHandler: IHandler {


    val backgrounds: List<IDataBackground>


}