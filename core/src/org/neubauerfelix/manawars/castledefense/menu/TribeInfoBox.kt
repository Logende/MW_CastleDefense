package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class TribeInfoBox(x: Float, y: Float, width: Float, val tribe: IDataTribe) : MComponentContainer(x, y) {


    init {
        val title = MTextLabel(0f, 0f, tribe.displayName, FontHandler.MWFont.BIG_HEADING, 1f)
        title.x = width / 2 - title.width / 2
        addComponent(title)

        val unitRunnable: (IDataUnit) -> Unit = {
            val submenu = CDInfoMenuUnit(MManaWars.m, it, tribe)
            MManaWars.m.startScreen(submenu, false)
        }

        val unitsInfoBox = UnitsInfoBox(0f, title.bottom, width, tribe.army.units,
                CDConstants.UI_MENU_MAIN_USE_DETAILED_UNIT_ICONS, unitRunnable)
        addComponent(unitsInfoBox)

        val buttonY = unitsInfoBox.bottom + MConstants.UI_DISTANCE_ROWS
        val button = MTextButtonSimple(0f, buttonY, "Fight", Runnable {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }, 0.5f)
        button.x = width / 2 - button.width / 2
        addComponent(button)
    }




}