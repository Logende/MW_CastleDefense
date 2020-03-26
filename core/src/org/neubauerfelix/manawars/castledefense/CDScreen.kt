package org.neubauerfelix.manawars.castledefense

import com.badlogic.gdx.Input
import org.neubauerfelix.manawars.castledefense.components.CDComponentControlPanel
import org.neubauerfelix.manawars.castledefense.player.CDControllerBot
import org.neubauerfelix.manawars.castledefense.player.CDControllerHuman
import org.neubauerfelix.manawars.castledefense.player.CDPlayer
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MBackground
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars


class CDScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {


    lateinit var match: CDMatch
    var scrollDirection: Int = 0
    var x: Float = 0f


    override fun loadScreen(): Boolean {
        val league = CDManaWars.cd.getLeagueHandler().getLeague("bronze")!!
        val army2 = league.getTribe("frost")!!
        val army1 = league.getTribe("zombie")!!
        val controllerA = CDControllerHuman()
        val controllerB = CDControllerBot()
        val playerA = CDPlayer(army1, controllerA, MConstants.TEAM_PLAYER)
        val playerB = CDPlayer(army2, controllerB, MConstants.TEAM_BOT)
        controllerA.player = playerA
        controllerB.player = playerB
        playerA.enemy = playerB
        playerB.enemy = playerA
        match = CDMatch(league, playerA, playerB, this)
        match.load()

        return false
    }

    override fun loadedScreen() {
        addComponent(CDComponentControlPanel())
        match.loadedAssets()

        var x = 2500f
        val league = CDManaWars.cd.getLeagueHandler().getLeague("bronze")
        league!!.buildingPlaceholder.produce(2000f, team = MConstants.TEAM_PEACEFUL, league = league)
        league!!.buildingPlaceholder.produce(3000f, team = MConstants.TEAM_PEACEFUL, league = league)
        league!!.buildingPlaceholder.produce(2500f, team = MConstants.TEAM_PEACEFUL, league = league)
        league!!.buildingPlaceholder.produce(1800f, team = MConstants.TEAM_PEACEFUL, league = league)
        for (building in CDManaWars.cd.getLeagueHandler().getLeague("bronze")!!.buildings) {
            //if (building.name.contains("heal")) {
                //building.produce(x, team = MConstants.TEAM_PLAYER)
                x += building.animationProducer.bodyWidth + 100f
          //  }
        }
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