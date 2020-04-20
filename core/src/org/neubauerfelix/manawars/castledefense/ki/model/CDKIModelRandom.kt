package org.neubauerfelix.manawars.castledefense.ki.model

import org.neubauerfelix.manawars.castledefense.ki.CDKILabel
import org.neubauerfelix.manawars.castledefense.ki.ICDKIModel

class CDKIModelRandom : ICDKIModel {

    override fun predict(features: Array<Double>): CDKILabel {
        return CDKILabel.values().random()
    }
}