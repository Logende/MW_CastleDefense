package org.neubauerfelix.manawars.castledefense.ki

interface ICDKI {

    fun predict(features: ICKIDFeatures): CDKILabel
}