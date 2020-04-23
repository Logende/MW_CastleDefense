package org.neubauerfelix.manawars.castledefense

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.Animation
import org.neubauerfelix.manawars.castledefense.components.CDComponentControlPanel
import org.neubauerfelix.manawars.castledefense.data.buildings.DataMine
import org.neubauerfelix.manawars.castledefense.player.CDControllerBot
import org.neubauerfelix.manawars.castledefense.player.CDControllerHuman
import org.neubauerfelix.manawars.castledefense.player.CDPlayer
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars


class CDScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {


    lateinit var match: CDMatch
    var scrollDirection: Int = 0
    var x: Float = 0f


    override fun loadScreen(): Boolean {
        val tribeHandler = CDManaWars.cd.getTribeHandler()
        val army1 = tribeHandler.getTribe("zombie")!!
        val army2 = tribeHandler.getTribe("skeleton")!!
        val controllerA = CDControllerHuman()
        val controllerB = CDControllerBot()
        val playerA = CDPlayer(army1, controllerA, MConstants.TEAM_PLAYER)
        val playerB = CDPlayer(army2, controllerB, MConstants.TEAM_BOT)
        controllerA.player = playerA
        controllerB.player = playerB
        playerA.enemy = playerB
        playerB.enemy = playerA
        match = CDMatch(playerA, playerB, this, playerB.tribe.playground)
        match.load()

        MManaWars.m.getMusicHandler().loadMusic(match.playerB.tribe.musicTrack)
        Thread.sleep(500) // TODO: FIX music issue. Music seems to be loaded async. Find a way to make sure it is loaded rather than just waiting stupidly
        return false
    }

    override fun loadedScreen() {
        addComponent(CDComponentControlPanel())
        match.loadedAssets()
        val dataMine = DataMine(25f, 20, 1, Animation(0f,
                MManaWars.m.getImageHandler().getTextureRegionMain("building.mine")))
        dataMine.produce(800f, GameConstants.BACKGROUND_HEIGHT - 300f, match.playerA.castle)
        dataMine.produce(match.playground.width - 800f, GameConstants.BACKGROUND_HEIGHT - 300f, match.playerB.castle)
        MManaWars.m.getMusicHandler().playMusic()
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