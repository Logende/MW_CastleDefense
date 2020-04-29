package org.neubauerfelix.manawars.castledefense.ki.machinelearning

import org.neubauerfelix.manawars.castledefense.ki.CDKILabel

interface ICDKIModel {

    fun predict(features: Array<Double>): CDKILabel
}