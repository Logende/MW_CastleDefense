package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.ILoadable
import org.neubauerfelix.manawars.game.entities.ILogicable


interface ICDController : ILogicable, ILoadable, IDisposable {


    var player: ICDPlayer
    val playerControlled: Boolean
    val analysis: ICDPlayerLiveAnalysis

    fun showControls()
    fun hideControls()

    fun executedUnitBuilding()


}