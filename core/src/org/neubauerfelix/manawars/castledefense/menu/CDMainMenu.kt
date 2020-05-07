package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.menu.MMenuScreenScrollable

class CDMainMenu(game: AManaWars) : MMenuScreenScrollable(game) {

    override fun loadMenu() {
    }

    override fun loadedMenu() {
        addTitle("ManaWars")

        val m = CDManaWars.cd
        val border = if (CDConstants.UI_MAIN_MENU_USE_DETAILED_UNIT_ICONS) 40f else 250f
        var boxY = 200f
        val boxWidth = if (CDConstants.UI_MAIN_MENU_USE_DETAILED_UNIT_ICONS) 800f else 550f
        var leftSide = true

        // TODO: Above the tribes in the center should be a "Your Army" tribe box with an "Edit" button
        // TODO Unit preview should already show bow?

        for (tribe in m.getTribeHandler().listTribes()) {
            val boxX = if (leftSide) border else GameConstants.SCREEN_WIDTH - border - boxWidth
            val box = TribeInfoBox(boxX, boxY, boxWidth, tribe)
            addComponent(box)
            if (!leftSide) {
                boxY += box.height + MConstants.UI_DISTANCE_COLUMNS * 5
            }
            leftSide = !leftSide
        }
    }

    override fun disposeMenu() {
    }

}