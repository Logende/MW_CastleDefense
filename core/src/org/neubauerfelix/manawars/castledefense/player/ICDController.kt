package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.game.entities.ILogicable


interface ICDController : ILogicable{

    val player: ICDPlayer
    val playerControlled: Boolean

    fun showControls()
    fun hideControls()


}