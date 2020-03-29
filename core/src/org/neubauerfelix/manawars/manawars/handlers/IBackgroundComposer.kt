package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.data.IDataBackground
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme

interface IBackgroundComposer : IHandler {

    fun composeBackgrounds(backgroundCount: Int, startThemes: Iterable<MWBackgroundTheme>,
                           endThemes: Iterable<MWBackgroundTheme>, subthemes: Iterable<MWBackgroundSubtheme>) :
            List<IDataBackground>
}