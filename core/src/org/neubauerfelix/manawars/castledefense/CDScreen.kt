package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars

class CDScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {


    lateinit var match: CDMatch
    override fun loadScreen(): Boolean {
        val background = MBackground(GameConstants.PATH_BACKGROUND + "1_1_0.jpg", 0, true, getGame().getAssetLoader())
        val army = MManaWars.m.getArmyHandler().listArmies().first()
        val controllerA = CDControllerBot(MConstants.TEAM_PLAYER)
        val controllerB = CDControllerBot(MConstants.TEAM_BOT)
        val playerA = CDPlayer(army, controllerA)
        val playerB = CDPlayer(army, controllerB)
        controllerA.player = playerA
        controllerB.player = playerB
        match = CDMatch(playerA, playerB, arrayListOf(background, background, background), this)
        match.load()
        return false
    }

    override fun loadedScreen() {
        match.loadedAssets()
    }

    override fun disposeScreen() {
        match.dispose()
    }

    override fun getIngameWindowX(): Float {
        return 0f
    }

    override fun logic(delta: Float, entities: List<IEntity>) {
        match.doLogic(delta)
        MManaWars.m.getCollisionHandler().updateCollisions(entities)
    }
}