package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.IDataBackground
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme
import java.lang.IllegalStateException
import kotlin.random.Random


class BackgroundComposer: IBackgroundComposer {

    override fun composeBackgrounds(backgroundCount: Int, startThemes: Iterable<MWBackgroundTheme>,
                                    endThemes: Iterable<MWBackgroundTheme>, subthemes: Iterable<MWBackgroundSubtheme>):
            List<IDataBackground> {
        require(backgroundCount >= 2 && endThemes.contains(MWBackgroundTheme.GRASSLAND)
                || backgroundCount >= 3)

        return try {
            composeBackgrounds(backgroundCount, startThemes, endThemes, subthemes, false)
        } catch (e: IllegalStateException) { // if unable to find backgrounds that contain only given themes:
            composeBackgrounds(backgroundCount, startThemes, endThemes, subthemes, true)
        }

    }


    fun composeBackgrounds(backgroundCount: Int, startThemes: Iterable<MWBackgroundTheme>,
                                    endThemes: Iterable<MWBackgroundTheme>, subthemes: Iterable<MWBackgroundSubtheme>,
                                    allowAnyThemeBetween: Boolean): List<IDataBackground> {
        val combinedThemes = startThemes.plus(endThemes)

        var backgrounds = MManaWars.m.getBackgroundListHandler().backgrounds
        // filter backgrounds: only allow some themes and subthemes
        backgrounds = backgrounds.filter {
            subthemes.contains(it.subtheme) && (allowAnyThemeBetween ||
                            combinedThemes.contains(it.startTheme) && combinedThemes.contains(it.endTheme))
        }

        var possibleStart = backgrounds.filter { startThemes.contains(it.startTheme) }
        var possibleEnd = backgrounds.filter { endThemes.contains(it.endTheme) }
        return matchBackgrounds(backgroundCount, possibleStart, backgrounds, possibleEnd)
    }


    fun matchBackgrounds(backgroundCount: Int, possibleStart: List<IDataBackground>,
                         possibleBetween: List<IDataBackground>, possibleEnd: List<IDataBackground>) :
            List<IDataBackground> {
        // 1. Create random ordered lists
        val possibilities = arrayListOf<List<IDataBackground>>()
        possibilities.add(possibleStart.shuffled(Random(Random.nextInt())))
        for (i in 0 until backgroundCount - 2) {
            possibilities.add(possibleBetween.shuffled(Random(Random.nextInt())))
        }
        possibilities.add((possibleEnd.shuffled(Random(Random.nextInt()))))

        // 2. Try out all combinations until there is success
        val result = ArrayList<IDataBackground>() // mutable! and will be changed by the following function
        val success = this.matchBackgrounds(result, possibilities)
        check(success)
        return result
    }

    fun matchBackgrounds(result: MutableList<IDataBackground>, remaining: MutableList<List<IDataBackground>>) :
            Boolean {
        return if (remaining.size == 0) {
            // Case 1: backgrounds successfully combined
            true
        } else {
            // Case 2: still something left to combine

            var validCombinationPossible = false

            val nextList = remaining.first()
            for (candidate in nextList) {
                if (validCombination(result.lastOrNull(), candidate)) {
                    result.add(candidate)
                    // candidate is valid

                    if (matchBackgrounds(result, remaining.subList(1, remaining.size))) {
                        // complete result with this background is possible
                        validCombinationPossible = true
                        break
                    }
                    result.remove(candidate) // if candidate would have been successful, break would have been called
                }
            }

            validCombinationPossible
        }
    }

    fun validCombination(first: IDataBackground?, next: IDataBackground) : Boolean {
        return if (first == null) {
            true
        } else {
            first.endTheme == next.startTheme
        }
    }

}
