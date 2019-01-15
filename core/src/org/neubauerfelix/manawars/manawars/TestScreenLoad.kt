package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.entities.IEntity

class TestScreenLoad(game: AManaWars) : GameScreenScreenTimed(game, false) {

    private val background: MBackground = MBackground(GameConstants.PATH_LOADING_SCREEN, 0, true, getGame().getAssetLoader())

    override fun loadScreen(): Boolean {
        background.load()
        return false
    }

    override fun loadedScreen() {
        background.loadedAssets()
        addBackground(background)
    }

    override fun disposeScreen() {
    }

    override fun getIngameWindowX(): Float {
        return 1f
    }

    override fun logic(delta: Float, entities: List<IEntity>) {
    }
}