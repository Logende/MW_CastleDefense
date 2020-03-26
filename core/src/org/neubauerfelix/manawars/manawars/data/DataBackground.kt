package org.neubauerfelix.manawars.manawars.data

import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme

class DataBackground(val name: String, val mirror: Boolean) : IDataBackground {

    override val startTheme: MWBackgroundTheme
    override val endTheme: MWBackgroundTheme
    override val subtheme: MWBackgroundSubtheme

    init {
        val parts = name.split("_")
        startTheme = MWBackgroundTheme.byId(parts[0].toInt())!!
        endTheme = MWBackgroundTheme.byId(parts[1].toInt())!!
        // do not care about background differentiator, which would be part with index 2
        subtheme = if (parts.size >= 4) {
            MWBackgroundSubtheme.byId(parts[3].toInt())!!
        } else {
            MWBackgroundSubtheme.DEFAULT
        }
    }

    override fun produce(x: Float): MBackground {
        return MBackground(name, x, mirror, MManaWars.m.getAssetLoader())
    }
}