package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class TribeInfoBox(x: Float, y: Float, width: Float, val tribe: IDataTribe, unitsScale: Float,
                   fightAction: (IDataTribe) -> Unit) :
        MComponentContainer(x, y) {


    init {
        val title = MTextLabel(0f, 0f, tribe.displayName, FontHandler.MWFont.BIG_HEADING, 3f)
        title.x = width / 2 - title.width / 2
        addComponent(title)

        val unitRunnable: (IDataUnit) -> Unit = {
            val submenu = CDInfoMenuUnit(MManaWars.m, it, tribe)
            MManaWars.m.startScreen(submenu, false)
        }

        val unitBoxY = title.bottom + MConstants.UI_DISTANCE_COLUMNS * 3
        val unitsInfoBox = UnitsInfoBox(0f, unitBoxY, width, tribe.army.units,
                CDConstants.UI_MENU_MAIN_USE_DETAILED_UNIT_ICONS, unitRunnable, unitsScale)
        addComponent(unitsInfoBox)

        val buttonY = unitsInfoBox.bottom + MConstants.UI_DISTANCE_COLUMNS * 3f
        val button = MTextButtonSimple(0f, buttonY, "Fight", Runnable {
            fightAction.invoke(tribe)
        }, 0.8f)
        button.x = width / 2 - button.width / 2
        addComponent(button)
    }




}