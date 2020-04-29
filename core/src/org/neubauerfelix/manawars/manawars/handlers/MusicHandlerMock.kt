package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.manawars.handlers.IMusicHandler


class MusicHandlerMock : IMusicHandler {


    @Synchronized
    override fun loadMusic(path: String) {
    }

    @Synchronized
    override fun playMusic() {
    }
}