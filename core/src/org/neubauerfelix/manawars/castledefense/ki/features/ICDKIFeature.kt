package org.neubauerfelix.manawars.castledefense.ki.features

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer

interface ICDKIFeature {

    val name: String
    fun extractFeature(player: ICDPlayer) : Double
}