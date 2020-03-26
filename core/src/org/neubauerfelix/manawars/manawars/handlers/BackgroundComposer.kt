package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.IDataBackground
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme


class BackgroundComposer: IBackgroundComposer {

    override fun composeBackgrounds(backgroundCount: Int, startThemes: Iterable<MWBackgroundTheme>?,
                                    endThemes: Iterable<MWBackgroundTheme>?, subthemes: Iterable<MWBackgroundSubtheme>?) :
            List<IDataBackground> {
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
        val possibilities = arrayListOf<List<IDataBackground>>()
        possibilities.add(possibleStart.shuffled())
        for (i in 0 until backgroundCount - 2) {
            possibilities.add(possibleBetween.shuffled())
        }
        possibilities.add((possibleEnd.shuffled()))

        // 2. Try out all combinations until there is success
        val result = ArrayList<IDataBackground>() // mutable! and will be changed by the following function
        val success = this.matchBackgrounds(result, possibilities)
        require(success)
        return result
    }

    private fun matchBackgrounds(result: MutableList<IDataBackground>, remaining: MutableList<List<IDataBackground>>) :
            Boolean {
        return if (remaining.size == 0) {
            // Case 1: backgrounds successfully combined
            true
        } else {
            // Case 2: still something left to combine

            var validCombinationPossible = false

            val nextList = remaining.first()
            for (candidate in nextList) {
                if (validCombination(result.firstOrNull(), candidate)) {
                    // candidate is valid

                    if (matchBackgrounds(result, remaining.subList(1, remaining.size))) {
                        // complete result with this background is possible
                        result.add(0, candidate) // add candidate in front of following backgrounds
                        validCombinationPossible = true
                        break
                    }

                }
            }

            validCombinationPossible
        }
    }

    private fun validCombination(first: IDataBackground?, next: IDataBackground) : Boolean {
        return if (first == null) {
            true
        } else {
            first.endTheme == next.startTheme
        }
    }

}
