package org.neubauerfelix.manawars.castledefense.ki

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer

class CDKIMachineLearning(val model: ICDKIModel, val featureExtractor: ICDKIFeatureExtractor) : ICDKI {

    override fun compute(player: ICDPlayer) : CDKILabel {
        val features = featureExtractor.generateFeatureVector(player)
        return model.predict(features)
    }

}