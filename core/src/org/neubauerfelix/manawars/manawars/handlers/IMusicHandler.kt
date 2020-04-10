package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler

interface IMusicHandler: IHandler {


    fun loadMusic(path: String) // note that this will unload the previously loaded track
    fun playMusic()


}