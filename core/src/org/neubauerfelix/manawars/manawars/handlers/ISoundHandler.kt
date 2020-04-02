package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler

interface ISoundHandler: IHandler {


    fun playSound(name: String, x: Float)


}