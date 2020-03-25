package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.components.CDComponentGameInfo
import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.ILoadableAsync
import org.neubauerfelix.manawars.game.IScreen
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.IBackground

class CDMatch(val league: IDataLeague, val playerA: ICDPlayer, val playerB: ICDPlayer,
              val backgrounds: Iterable<IBackground>, val screen: IScreen) :
        ILoadableAsync, IDisposable, ILogicable {

    var loaded = false

    override fun load() {
        require(!loaded)
        backgrounds.forEach { background ->
            background.load()
        }
    }

    override fun loadedAssets() {
        require(!loaded)
        backgrounds.forEach { background ->
            background.loadedAssets()
            screen.addBackground(background, backgrounds.count())
        }
        loaded = true
        playerA.spawnCastle(true, GameConstants.BACKGROUND_WIDTH * backgrounds.count())
        playerB.spawnCastle(false, GameConstants.BACKGROUND_WIDTH * backgrounds.count())
        playerA.controller.showControls()
        playerB.controller.showControls()
        screen.addComponent(CDComponentGameInfo(30f, 200f, playerA))
    }

    override fun isLoaded(): Boolean {
        return loaded
    }

    override fun dispose() {
        require(loaded)
        playerA.controller.hideControls()
        playerB.controller.hideControls()
        backgrounds.forEach { background ->
            screen.removeBackground(background)
            background.dispose()
        }
        loaded = false
    }

    override fun doLogic(delta: Float) {
        playerA.controller.doLogic(delta)
        playerB.controller.doLogic(delta)
    }
}