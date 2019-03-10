package org.neubauerfelix.manawars.castledefense

import com.badlogic.gdx.Input
import org.neubauerfelix.manawars.castledefense.components.CDComponentControlPanel
import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.castledefense.player.CDControllerBot
import org.neubauerfelix.manawars.castledefense.player.CDPlayer
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.components.GameComponent
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars



class CDScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {


    lateinit var match: CDMatch
    var scrollDirection: Int = 0
    var x: Float = 0f


    override fun loadScreen(): Boolean {
        val background = MBackground(GameConstants.PATH_BACKGROUND + "1_1_0.jpg", 0f, true, getGame().getAssetLoader())
        val background2 = MBackground(GameConstants.PATH_BACKGROUND + "1_1_0.jpg", GameConstants.BACKGROUND_WIDTH*1, true, getGame().getAssetLoader())
        val background3 = MBackground(GameConstants.PATH_BACKGROUND + "1_1_0.jpg", GameConstants.BACKGROUND_WIDTH*2, true, getGame().getAssetLoader())
        val army = MManaWars.m.getArmyHandler().listArmies().first()
        val controllerA = CDControllerBot()
        val controllerB = CDControllerBot()
        val playerA = CDPlayer(army, controllerA, MConstants.TEAM_PLAYER)
        val playerB = CDPlayer(army, controllerB, MConstants.TEAM_BOT)
        controllerA.player = playerA
        controllerB.player = playerB
        playerA.enemy = playerB
        playerB.enemy = playerA
        match = CDMatch(playerA, playerB, arrayListOf(background, background2, background3), this)
        match.load()
        return false
    }

    override fun loadedScreen() {
        match.loadedAssets()
        addComponent(CDComponentControlPanel())
        val unit = match.playerA.army.units.first()
        addComponent(CDComponentUnit(30f, GameConstants.WORLD_HEIGHT, GameConstants.CONTROLPANEL_BUTTON_SIZE,
                GameConstants.CONTROLPANEL_BUTTON_SIZE, unit))
    }

    override fun disposeScreen() {
        match.dispose()
    }

    override fun getIngameWindowX(): Float {
        return x
    }

    override fun logic(delta: Float, entities: List<IEntity>) {
        x += scrollDirection * delta * 1000
        match.doLogic(delta)
        MManaWars.m.getCollisionHandler().updateCollisions(entities)
    }


    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.LEFT -> {
                scrollDirection = -1
                return true
            }
            Input.Keys.RIGHT -> {
                scrollDirection = 1
                return true
            }
            else -> {}
        }
        return super.keyDown(keycode)
    }

    override fun keyUp(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.LEFT -> {
                scrollDirection = 0
                return true
            }
            Input.Keys.RIGHT -> {
                scrollDirection = 0
                return true
            }
            else -> {}
        }
        return super.keyUp(keycode)
    }




}