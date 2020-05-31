package org.neubauerfelix.manawars.manawars.menu

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.components.MTextButtonSimple
import org.neubauerfelix.manawars.manawars.components.MTextLabel
import org.neubauerfelix.manawars.manawars.handlers.FontHandler.MWFont


abstract class MMenuScreen(game: AManaWars, drawComponentsStatic: Boolean = false) :
        GameScreenScreenTimed(game, true, drawComponentsStatic) {



    override fun loadScreen(): Boolean {
        loadMenu()
        return false
    }

    override fun loadedScreen() {
        loadedMenu()
    }

    override fun disposeScreen() {
        disposeMenu()
    }

    override fun getIngameWindowX(): Float {
        return 0f
    }



    fun closeScreen() {
        require(game.screen == this)
        game.stopScreen()
    }



    fun addTextButton(text: String, action: Runnable? = null, x: Float, centreX: Boolean, y: Float,
                      centreY: Boolean, scale: Float = 1f): MTextButtonSimple {
        val button = MTextButtonSimple(x, y, text, action, scale)
        if (centreX) {
            button.x +=  GameConstants.SCREEN_WIDTH / 2f - button.width / 2f
        }
        if (centreY) {
            button.y +=  GameConstants.SCREEN_HEIGHT / 2f - button.height / 2f
        }
        addComponent(button)
        return button
    }

    fun addActionButton(text: String, action: Runnable, x: Float, centreX: Boolean,
                        y: Float = MConstants.MENU_ACTION_BUTTON_Y, centreY: Boolean = false): MTextButtonSimple {
        return addTextButton(text, action, x, centreX, y, centreY)
    }

    fun addExitButton(text: String, x: Float, centreX: Boolean,
                      y: Float = MConstants.MENU_ACTION_BUTTON_Y, centreY: Boolean = false): MTextButtonSimple {
        return addTextButton(text, Runnable { closeScreen() }, x, centreX, y, centreY)
    }


    fun addTextLabel(text: String, font: MWFont, x: Float, centreX: Boolean, y: Float, centreY: Boolean): MTextLabel {
        val button = MTextLabel(x, y, text, font)
        if (centreX) {
            button.x +=  GameConstants.SCREEN_WIDTH / 2f - button.width / 2f
        }
        if (centreY) {
            button.y +=  GameConstants.SCREEN_HEIGHT / 2f - button.height / 2f
        }
        addComponent(button)
        return button
    }

    fun addTextLabel(text: String, x: Float, centreX: Boolean, y: Float, centreY: Boolean): MTextLabel {
        return addTextLabel(text, MWFont.MAIN, x, centreX, y, centreY)
    }

    fun addTitle(text: String): MTextLabel {
        return addTextLabel(text, 0f, true, MConstants.MENU_TITLE_Y, false)
    }

    // TODO: Text Set support?
    // TODO addContent and addContentHalf(sideLeft: Boolean) support?

    abstract fun loadMenu()
    abstract fun loadedMenu()
    abstract fun disposeMenu()

}