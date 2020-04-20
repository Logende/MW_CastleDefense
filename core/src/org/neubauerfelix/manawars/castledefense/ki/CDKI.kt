package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer

class CDKI(val model: ICDKIModel, val featureExtractor: ICDKIFeatureExtractor) {

    fun compute(player: ICDPlayer) : CDKILabel {
        val features = featureExtractor.generateFeatureVector(player)
        return model.predict(features)
    }

}