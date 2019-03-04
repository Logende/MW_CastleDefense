package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.game.entities.ILogicable


interface ICDController : ILogicable{


    val playerControlled: Boolean

    fun showControls()
    fun hideControls()


}