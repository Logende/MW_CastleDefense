package org.neubauerfelix.manawars.manawars

import com.badlogic.gdx.utils.Timer
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillExample
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerTest

class TestScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {

    private val background: MBackground = MBackground(GameConstants.PATH_BACKGROUND + "1_1_0.jpg", 0, true, getGame().getAssetLoader())

    private lateinit var skill: IDataSkill

    override fun loadScreen(): Boolean {
        background.load()

        skill = DataSkillExample()
        skill.loadAsset()



        return false
    }

    override fun loadedScreen() {
        background.loadedAssets()
        addBackground(background)
        skill.loadedAsset()


        val skinName = "dwarf.1.1"
        val skinNameMount = "lion"
        val animationProducer = IEntityAnimationProducer.createProducerHuman(skinName)
        val animationProducerMount = IEntityAnimationProducer.createProducerMount(skinNameMount)
        val animationProducerRider = IEntityAnimationProducer.createProducerRider(skinNameMount, skinName)


        val controller = ControllerTest()
        val e = MEntityControlled(animationProducerRider, 100f, 1000f, arrayOf(skill), 100f, controller = controller)
        controller.controlled = e
        e.setLocation(300f, 0f)
        e.spawn()
        e.gravity()
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                e.executeAction(0)
            }
        }, 5f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                e.executeAction(0)
            }
        }, 10f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                e.executeAction(0)
            }
        }, 15f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                e.executeAction(0)
            }
        }, 20f, 2f)

        Timer.schedule(object : Timer.Task() {
            override fun run() {
                e.animation.updateAnimation(e)
            }
        }, 1f, 2f)

    }

    override fun disposeScreen() {
    }

    override fun getIngameWindowX(): Float {
        return 1f
    }
}