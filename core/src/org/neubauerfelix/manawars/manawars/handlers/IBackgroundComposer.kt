package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.IDataBackground
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme

interface IBackgroundComposer : IHandler {

    // TODO: This background composer. Then actively use playground in CDMatch/CDScreen including using the background composer
    fun composeBackgrounds(backgroundCount: Int, startThemes: List<MWBackgroundTheme>?,
                           endThemes: List<MWBackgroundTheme>?, subthemes: List<MWBackgroundSubtheme>?) :
            List<IDataBackground>
}