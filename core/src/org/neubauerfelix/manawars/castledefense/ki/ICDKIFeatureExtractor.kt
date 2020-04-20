package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer

interface ICDKIFeatureExtractor {

    fun generateFeatureVector(player: ICDPlayer) : Array<Double>
}