package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.IDataBackground
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme


class BackgroundComposer: IBackgroundComposer {

    override fun composeBackgrounds(backgroundCount: Int, startThemes: List<MWBackgroundTheme>?,
                                    endThemes: List<MWBackgroundTheme>?, subthemes: List<MWBackgroundSubtheme>?) :
    List<IDataBackground {
        require(backgroundCount >= 3)
        var backgrounds = MManaWars.m.getBackgroundListHandler().backgrounds
        if (subthemes != null) {
            backgrounds = backgrounds.filter { subthemes.contains(it.subtheme) }
        }
        val possibleStart = if (startThemes != null) {
            backgrounds.filter { startThemes.contains(it.startTheme) }
        } else {
            backgrounds
        }
        val possibleEnd = if (endThemes != null) {
            backgrounds.filter { endThemes.contains(it.endTheme) }
        } else {
            backgrounds
        }
        return matchBackgrounds(backgroundCount, possibleStart, backgrounds, possibleEnd)
    }

    private fun matchBackgrounds(backgroundCount: Int, possibleStart: List<IDataBackground>,
                                   possibleBetween: List<IDataBackground>, possibleEnd: List<IDataBackground>) :
            List<IDataBackground> {
        // 1. Create random ordered lists
        val possibilities = List(backgroundCount) {
            when (it) {
                0 -> possibleStart.shuffled()
                backgroundCount-1 -> possibleEnd.shuffled()
                else -> possibleBetween.shuffled()
            }
        }


        // 2. Try out all combinations until there is success
    }

    private fun matchBackgrounds(current: IDataBackground, following: List<List<IDataBackground>>) {
        
    }

}
