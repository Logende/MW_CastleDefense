package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.game.entities.ILogicable


interface ICDController : ILogicable{

    val player: ICDPlayer
    val playerControlled: Boolean
    val analysis: ICDPlayerLiveAnalysis

    fun showControls()
    fun hideControls()


}