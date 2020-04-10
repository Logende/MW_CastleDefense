package org.neubauerfelix.manawars.manawars.data

import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme
import java.lang.NullPointerException

class DataBackground(val name: String, val mirror: Boolean) : IDataBackground {

    override val startTheme: MWBackgroundTheme
    override val endTheme: MWBackgroundTheme
    override val subtheme: MWBackgroundSubtheme

    init {
        val parts = name.split("_")
        try {
            val startIndex = if (mirror) 1 else 0
            val endIndex = if (mirror) 0 else 1
            startTheme = MWBackgroundTheme.byId(parts[startIndex].toInt())!!
            endTheme = MWBackgroundTheme.byId(parts[endIndex].toInt())!!
            // do not care about background differentiator, which would be part with index 2
            subtheme = if (parts.size >= 4) {
                MWBackgroundSubtheme.byId(parts[3].toInt())!!
            } else {
                MWBackgroundSubtheme.DEFAULT
            }
        } catch (e: NullPointerException) {
            print("Unable to find themes for background $name.")
            throw e
        }
    }

    override fun produce(x: Float): MBackground {
        return MBackground("backgrounds/$name.jpg", x, mirror, MManaWars.m.getAssetLoader())
    }
}