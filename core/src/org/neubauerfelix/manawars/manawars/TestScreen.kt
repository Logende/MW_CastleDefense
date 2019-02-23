package org.neubauerfelix.manawars.manawars

import com.badlogic.gdx.utils.Timer
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillLoaded
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.ITeamable
import org.neubauerfelix.manawars.manawars.entities.MEntityControlled
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerTest
import org.neubauerfelix.manawars.manawars.entities.skills.Skill
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType

class TestScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {

    private val background: MBackground = MBackground(GameConstants.PATH_BACKGROUND + "1_1_0.jpg", 0, true, getGame().getAssetLoader())

    private lateinit var skill: IDataAction

    override fun loadScreen(): Boolean {
        background.load()

        MManaWars.m.getSkillAnalysisHandler().analyseSkills("skillanalysis.yml")

        skill = MManaWars.m.getActionHandler().getAction("arrowrain_single")!!
        skill.loadAsset()

        /**
         * TODO:
         * Prüfen, wie Äxte und andere Skills mit variabler X-Geschwindigkeit ihren Range bekommen
         * Range, Kosten und Angriffstyp von Skills berechnen (sowie andere Skill Eigenschaften)
         * Summon Skills einbauen
         * Einheit Movement AI
         * Tribes einbauen
         * Datenklassen zu Kotlin Data Class machen
         * Sounds einbauen
         * Testweise Steuerung um manuell zu steuern und besser testen zu können
         * Castles einbauen
         *
         *
         */



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
        val a = MEntityControlled(animationProducer, 100f, 1000f, arrayOf(skill), 100f, controller = controller)
        controller.controlled = a
        a.setLocation(300f, 0f)
        a.spawn()
        a.team = MConstants.TEAM_BOT
        a.gravity()
        a.animation.updateAnimation(a)

        val b = MEntityControlled(animationProducerRider, 100f, 1000f, arrayOf(skill), 100f, controller = controller)
        controller.controlled = b
        b.setLocation(200f, 0f)
        b.spawn()
        b.gravity()
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                a.executeAction(0)
            }
        }, 5f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                a.executeAction(0)
            }
        }, 10f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                a.executeAction(0)
            }
        }, 15f)
        Timer.schedule(object : Timer.Task() {
            override fun run() {
                a.executeAction(0)
            }
        }, 2f, 0.5f)
        b.team = MConstants.TEAM_PLAYER

    }

    override fun logic(delta: Float, entities: List<IEntity>) {
        MManaWars.m.getCollisionHandler().updateCollisions(entities)
    }

    override fun disposeScreen() {
    }

    override fun getIngameWindowX(): Float {
        return 1f
    }
}