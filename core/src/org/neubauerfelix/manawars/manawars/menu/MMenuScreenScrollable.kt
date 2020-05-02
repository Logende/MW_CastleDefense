package org.neubauerfelix.manawars.manawars.menu

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.MTextButtonSimple
import org.neubauerfelix.manawars.manawars.components.MTextLabel
import org.neubauerfelix.manawars.manawars.handlers.FontHandler.MWFont
import kotlin.math.max


abstract class MMenuScreenScrollable(game: AManaWars) : MMenuScreen(game) {

    private val scrollComponent = MScrollComponent(0f, 0f, GameConstants.SCREEN_WIDTH,
            GameConstants.SCREEN_HEIGHT)

    var currentWindowY = 0f

    override fun loadedScreen() {
        addComponent(scrollComponent)
        super.loadedScreen()
        var bottom = 0f
        for (component in this.getComponents()) {
           bottom = max(bottom, component.bottom)
        }
        this.scrollComponent.height = max(this.scrollComponent.height, bottom + MConstants.UI_LINE_DISTANCE)
    }


    override fun pause() {
        super.pause()
        val window = MManaWars.m.getCamera().window
        this.currentWindowY = window.y
        window.y = 0f
    }

    override fun dispose() {
        super.dispose()
        val window = MManaWars.m.getCamera().window
        this.currentWindowY = window.y
        window.y = 0f
    }

    override fun resume() {
        super.resume()
        val window = MManaWars.m.getCamera().window
        window.y = currentWindowY
    }

}