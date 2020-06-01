package org.neubauerfelix.manawars.tools

import com.badlogic.gdx.Gdx
import org.neubauerfelix.manawars.castledefense.CDMatchConfiguration
import org.neubauerfelix.manawars.castledefense.CDScreen
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.ScreenState
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.events.EntityLivingSpawnEvent
import org.neubauerfelix.manawars.game.events.IEvent
import org.neubauerfelix.manawars.game.events.Listener
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.events.EntityDamageEvent
import org.neubauerfelix.manawars.manawars.events.EntityHealEvent
import java.util.*

class EvaluationScreen(game: AManaWars, val config: IEvaluationConfig, val outputConfigPath: String) :
        GameScreenScreenTimed(game, true, true) {

    val matches: MutableList<CDMatchConfiguration> = ArrayList(config.matches)
    val results: MutableList<IEvaluationResult> = mutableListOf()

    lateinit var currentScreen: CDScreen
    lateinit var currentResult: EvaluationResult
    var wroteResults = false

    init {
        require(matches.isNotEmpty())
        if (Evaluation.ADVANCED_EVALUATION) {
            registerEventListeners(game)
        }
    }


    fun startMatch() {
        require(!this::currentScreen.isInitialized || currentScreen.getState() == ScreenState.DISPOSED)

        val matchConfig = matches.first()
        matches.removeAt(0)

        currentResult = if (Evaluation.ADVANCED_EVALUATION) EvaluationResultAdvanced() else EvaluationResult()
        currentResult.match = matchConfig

        currentScreen = CDScreen(game, matchConfig)
        game.startScreen(currentScreen, false)

        EvaluationEntity(config).spawn()
        if (this::currentResult.isInitialized) {
            results.add(currentResult)
        }
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
            writeMatchResults(currentScreen, currentResult)
            if (matches.isNotEmpty()) {
                startMatch()
            } else if (!wroteResults){
                wroteResults = true
                println("Finished all matches. Exiting")
                Evaluation.writeResults(results, outputConfigPath)
                Gdx.app.exit()
            }
        }
    }

    private fun writeMatchResults(screen: CDScreen, result: EvaluationResult) {
        val match = screen.match
        val playerA = match.playerA
        val playerB = match.playerB
        result.matchDuration = screen.getGameTime()
        result.victoryType = if (playerA.castle.health <= 0f) {
            IEvaluationResult.VictoryType.PLAYER_B
        } else if (playerB.castle.health <= 0f) {
            IEvaluationResult.VictoryType.PLAYER_A
        } else if (result.matchDuration > config.maxMatchLength) {
            IEvaluationResult.VictoryType.TIMEOUT
        } else {
            IEvaluationResult.VictoryType.TOO_MANY_UNITS
        }
    }

    fun registerEventListeners(game: AManaWars) {
        val eventHandler = game.getEventHandler()
        eventHandler.registerListener(EntityDamageEvent::class.java.name, object : Listener() {
            override fun handleEvent(event: IEvent) {
                val e = event as EntityDamageEvent
                if (e.cancelled) {
                    return
                }
                val list = (currentResult as EvaluationResultAdvanced).events
                val entity = event.entity
                if (entity is IControlled) {
                    list.add(EventWriter.writeDamageEvent(e, entity))
                }
            }
        })
        // death event not neccessary because damage event also covers death (deadly hit attribute)
        eventHandler.registerListener(EntityHealEvent::class.java.name, object : Listener() {
            override fun handleEvent(event: IEvent) {
                val e = event as EntityHealEvent
                if (e.cancelled) {
                    return
                }
                val list = (currentResult as EvaluationResultAdvanced).events
                val entity = event.entity
                if (entity is IControlled) {
                    list.add(EventWriter.writeHealEvent(e, entity))
                }
            }
        })
        eventHandler.registerListener(EntityLivingSpawnEvent::class.java.name, object : Listener() {
            override fun handleEvent(event: IEvent) {
                val e = event as EntityLivingSpawnEvent
                val list = (currentResult as EvaluationResultAdvanced).events
                val entity = event.entity
                if (entity is IControlled) {
                    list.add(EventWriter.writeSpawnEvent(e, entity))
                }
            }
        })

    }
}