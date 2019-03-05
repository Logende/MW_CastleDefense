package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.player.ICDPlayer
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.ILoadableAsync
import org.neubauerfelix.manawars.game.IScreen
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.IBackground

class CDMatch(val playerA: ICDPlayer, val playerB: ICDPlayer, val backgrounds: Iterable<IBackground>, val screen: IScreen) :
        ILoadableAsync, IDisposable, ILogicable {

    var loaded = false

    override fun load() {
        require(!loaded)
        playerA.army.loadAsset()
        playerB.army.loadAsset()
        backgrounds.forEach { background ->
            background.load()
        }
    }

    override fun loadedAssets() {
        require(!loaded)
        playerA.army.loadedAsset()
        playerB.army.loadedAsset()
        backgrounds.forEach { background ->
            background.loadedAssets()
            screen.addBackground(background, backgrounds.count())
        }
        loaded = true
        playerA.spawnCastle(true, GameConstants.BACKGROUND_WIDTH * backgrounds.count())
        playerB.spawnCastle(false, GameConstants.BACKGROUND_WIDTH * backgrounds.count())
    }

    override fun isLoaded(): Boolean {
        return loaded
    }

    override fun dispose() {
        require(loaded)
        playerA.army.disposeAsset()
        playerB.army.disposeAsset()
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