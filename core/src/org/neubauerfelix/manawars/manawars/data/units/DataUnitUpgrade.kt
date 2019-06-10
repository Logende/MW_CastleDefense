package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.manawars.enums.MWUpgrade

data class DataUnitUpgrade(val type: MWUpgrade, val value: Float) {

    val valueDisplay: String
        get() = if (type.isPercentage) {
            (value * 100).toInt().toString()
        } else {
            "+"
        }

}