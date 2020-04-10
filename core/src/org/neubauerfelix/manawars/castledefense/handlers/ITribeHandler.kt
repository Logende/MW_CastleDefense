package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.IHandler

interface ITribeHandler: IHandler {


    fun putTribe(tribe: IDataTribe)
    fun getTribe(name: String): IDataTribe?
    fun listTribes(): Iterable<IDataTribe>


}