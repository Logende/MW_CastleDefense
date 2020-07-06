package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.components.CDComponentGameInfo
import org.neubauerfelix.manawars.castledefense.data.IDataPlayground
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.ILoadableAsync
import org.neubauerfelix.manawars.game.IScreen
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.IBackground
import org.neubauerfelix.manawars.manawars.MManaWars

class CDMatch(val playerA: ICDPlayer, val playerB: ICDPlayer, val screen: IScreen, val playground: IDataPlayground) :
        ILoadableAsync, IDisposable, ILogicable {

    val backgrounds: Iterable<IBackground>
    var nextCycleTime: Long = Long.MAX_VALUE

    init {
        val backgroundData = MManaWars.m.getBackgroundComposer().composeBackgrounds(playground.backgroundCount,
                listOf(playerA.tribe.backgroundTheme),
                listOf(playerB.tribe.backgroundTheme),
                playerA.tribe.backgroundSubthemes.plus(playerB.tribe.backgroundSubthemes).
                        toList())
        backgrounds = backgroundData.mapIndexed { index, background ->
            background.produce(index * GameConstants.BACKGROUND_WIDTH) }
    }

    var loaded = false

    override fun load() {
        require(!loaded)
        backgrounds.forEach { background ->
            background.load()
        }
        playerA.load()
        playerB.load()
    }

    override fun loadedAssets() {
        require(!loaded)
        backgrounds.forEach { background ->
            background.loadedAssets()
            screen.addBackground(background, backgrounds.count())
        }
        loaded = true
        nextCycleTime = MManaWars.m.screen.getGameTime() + CDConstants.UNIT_BUILD_CYCLE_TIME
        playerA.spawnCastle(true, GameConstants.BACKGROUND_WIDTH * backgrounds.count())
        playerB.spawnCastle(false, GameConstants.BACKGROUND_WIDTH * backgrounds.count())
        playground.createPlayground(playerA, playerB)
        playerA.controller.showControls()
        playerB.controller.showControls()
        screen.addComponent(CDComponentGameInfo(GameConstants.CONTROLPANEL_INFO_FIELD_X,
                GameConstants.CONTROLPANEL_Y + GameConstants.CONTROLPANEL_INFO_FIELD_BORDER, playerA))
    }

    override fun isLoaded(): Boolean {
        return loaded
    }

    override fun dispose() {
        require(loaded)
        playerA.dispose()
        playerB.dispose()
        backgrounds.forEach { background ->
            screen.removeBackground(background)
            background.dispose()
        }
        loaded = false
    }

    override fun doLogic(delta: Float) {
        if (MManaWars.m.screen.getGameTime() >= nextCycleTime) {
            playerA.executeUnitBuilding()
            playerB.executeUnitBuilding()
            nextCycleTime = MManaWars.m.screen.getGameTime() + CDConstants.UNIT_BUILD_CYCLE_TIME
            println("unit building tick")
        }
        playerA.controller.doLogic(delta)
        playerB.controller.doLogic(delta)

        if (playerA.castle.remove || playerB.castle.remove) {

            MManaWars.m.screen.remove = true // TODO: Victory screen
        }
    }
}