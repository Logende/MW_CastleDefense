package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerTest

class TestScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {

    private val background: MBackground = MBackground(GameConstants.PATH_LOADING_SCREEN, 0, true, getGame().getAssetLoader())

    override fun loadScreen(): Boolean {
        background.load()


        val skinName = "dwarf.1.1"
        val skinNameMount = "lion"
        val animationProducer = IEntityAnimationProducer.createProducerHuman(skinName)
        val animationProducerMount = IEntityAnimationProducer.createProducerMount(skinNameMount)
        val animationProducerRider = IEntityAnimationProducer.createProducerRider(skinNameMount, skinName)


        val controller = ControllerTest()
        val e = MEntityControlled(animationProducerRider, 100f, 100f, arrayOf(), 1f, controller = controller)
        controller.controlled = e
        e.setLocation(300f, 0f)
        e.spawn()
        e.gravity()

        return false
    }

    override fun loadedScreen() {
        background.loadedAssets()

    }

    override fun disposeScreen() {
    }

    override fun getIngameWindowX(): Float {
        return 1f
    }
}