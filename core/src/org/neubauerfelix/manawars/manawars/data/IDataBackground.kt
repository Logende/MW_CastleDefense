package org.neubauerfelix.manawars.manawars.data

import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme

interface IDataBackground {

    val startTheme: MWBackgroundTheme
    val endTheme: MWBackgroundTheme
    val subtheme: MWBackgroundSubtheme
    // val mirror: Boolean <- do not show this property in the interface

    fun produce(x: Float): MBackground
}