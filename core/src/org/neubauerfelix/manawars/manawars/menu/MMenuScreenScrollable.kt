package org.neubauerfelix.manawars.manawars.menu

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.components.MTextButtonSimple
import org.neubauerfelix.manawars.manawars.components.MTextLabel
import org.neubauerfelix.manawars.manawars.handlers.FontHandler.MWFont
import kotlin.math.max


abstract class MMenuScreenScrollable(game: AManaWars) : MMenuScreen(game) {

    private val scrollComponent = MScrollComponent(0f, 0f, GameConstants.SCREEN_WIDTH,
            GameConstants.SCREEN_HEIGHT)

    override fun loadedScreen() {
        addComponent(scrollComponent)
        super.loadedScreen()
    }


    override fun addComponent(component: IComponent) {
        super.addComponent(component)
        this.scrollComponent.height = max(this.scrollComponent.height, component.bottom + MConstants.UI_LINE_DISTANCE + 100f)
    }




}