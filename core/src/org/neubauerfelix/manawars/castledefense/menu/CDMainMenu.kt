package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.menu.MMenuScreen

class CDMainMenu(game: AManaWars) : MMenuScreen(game) {

    override fun loadMenu() {
    }

    override fun loadedMenu() {
        addTitle("ManaWars")

        val m = CDManaWars.cd
        val x = 40f
        var y = 150f
        val width = MConstants.BODY_RIDER_WIDTH.toFloat()
        val height = MConstants.BODY_HUMAN_HEIGHT.toFloat()
        for (tribe in m.getTribeHandler().listTribes()) {
            val melee = tribe.army.units[1]
            val animation = melee.animation.produce(x, y, width, height)
            addEntity(animation)
            y += height + MConstants.UI_DISTANCE_COLUMNS
        }
    }

    override fun disposeMenu() {
    }
}