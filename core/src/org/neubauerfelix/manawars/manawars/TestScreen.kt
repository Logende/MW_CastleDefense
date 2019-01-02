package org.neubauerfelix.manawars.manawars

import com.badlogic.gdx.utils.Timer
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimated
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.animation.human.EntityAnimationProducerHuman
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyDataHuman

class TestScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {

    private val background: MBackground = MBackground(GameConstants.PATH_LOADING_SCREEN, 0, true, getGame().getAssetLoader())

    override fun loadScreen(): Boolean {
        background.load()


        val skinName = "dwarf.1.1"
        val animationProducer = IEntityAnimationProducer.createProducerHuman(skinName)
        val entity = MEntityAnimated(animationProducer, 10f)
        entity.spawn()
        entity.x = 500f
        entity.y = 500f
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                entity.gravitiy()
            }
        }, 2f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                entity.speedX = 100f
                entity.jump()
            }
        }, 10f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                entity.speedX = 100f
                entity.jump()
            }
        }, 10.5f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                entity.speedX = 100f
                entity.jump()
            }
        }, 11f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                entity.speedX = 100f
            }
        }, 14f)
        animationProducer.produce(200f, 200f, 1000f, 1000f).spawn()
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