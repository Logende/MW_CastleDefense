package org.neubauerfelix.manawars.tools

import com.badlogic.gdx.Gdx
import org.neubauerfelix.manawars.castledefense.CDMatchConfiguration
import org.neubauerfelix.manawars.castledefense.CDScreen
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.ScreenState
import org.neubauerfelix.manawars.game.entities.IEntity
import java.util.*

class EvaluationScreen(game: AManaWars, val config: IEvaluationConfig) :
        GameScreenScreenTimed(game, true, true) {

    val matches: MutableList<CDMatchConfiguration> = ArrayList(config.matches)
    lateinit var currentScreen: CDScreen

    init {
        require(matches.isNotEmpty())
    }


    fun startMatch() {
        require(!this::currentScreen.isInitialized || currentScreen.getState() == ScreenState.DISPOSED)
        val matchConfig = matches.first()
        matches.removeAt(0)
        currentScreen = CDScreen(getGame(), matchConfig)
        getGame().startScreen(currentScreen, false)
        EvaluationEntity(config).spawn()
        println("Started match of ${matchConfig.tribeA.name} vs ${matchConfig.tribeB.name}")
    }

    override fun loadScreen(): Boolean {
        return false
    }

    override fun loadedScreen() {
        Timer().schedule(
                object : TimerTask() {
                    override fun run() {
                        startMatch()
                    }
                },
                10
        )
    }

    override fun disposeScreen() {
    }

    override fun getIngameWindowX(): Float {
        return 0f
    }

    override fun logic(delta: Float, entities: List<IEntity>) {
        super.logic(delta, entities)

        if (this::currentScreen.isInitialized && currentScreen.getState() == ScreenState.DISPOSED) {
            if (matches.isNotEmpty()) {
                startMatch()
            } else {
                println("Finished all matches. Exiting")
                Gdx.app.exit()
            }
        }
        // TODO: abort game if timeout reached or other abort condition (maybe via listener)
    }
}