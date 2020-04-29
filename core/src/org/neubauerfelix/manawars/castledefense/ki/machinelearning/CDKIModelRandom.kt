package org.neubauerfelix.manawars.castledefense.ki.machinelearning

import org.neubauerfelix.manawars.castledefense.ki.CDKILabel

class CDKIModelRandom : ICDKIModel {

    override fun predict(features: Array<Double>): CDKILabel {
        return CDKILabel.values().random()
    }
}