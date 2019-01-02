package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.entities.animation.human.IBodyDataHuman

interface IBodyDataHandler: IHandler {


    fun getBodyDataHuman(name: String): IBodyDataHuman


}