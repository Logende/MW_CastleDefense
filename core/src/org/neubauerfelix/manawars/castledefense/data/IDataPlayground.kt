package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants

interface IDataPlayground {

    // NOTE: Playground is NOT responsible for background images, only for background count and stuff like buildings

    companion object {
        val BACKGROUND_COUNT = 3
        val PLAYGROUND_WIDTH = BACKGROUND_COUNT * GameConstants.BACKGROUND_WIDTH
    }


    val name: String
    fun createPlayground(playerA: ICDPlayer, playerB: ICDPlayer, league: IDataLeague)
}