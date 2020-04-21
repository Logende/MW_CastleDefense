package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants

interface IDataPlayground {

    // NOTE: Playground is NOT responsible for background images, only for background count and stuff like buildings


    val backgroundCount: Int
    val name: String
    fun createPlayground(playerA: ICDPlayer, playerB: ICDPlayer)
}