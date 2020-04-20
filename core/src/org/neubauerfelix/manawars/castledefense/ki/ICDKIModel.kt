package org.neubauerfelix.manawars.castledefense.ki

interface ICDKIModel {

    fun predict(features: Array<Double>): CDKILabel
}