package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameScreenScreenTimed
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataArmy
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerTest

class TestScreen(game: AManaWars) : GameScreenScreenTimed(game, false) {

    private val background: MBackground = MBackground(GameConstants.PATH_BACKGROUND + "1_1_0.jpg", 0f, true, getGame().getAssetLoader())

    private lateinit var skill: IDataAction

    var army: IDataArmy? = null

    override fun loadScreen(): Boolean {
        background.load()


       // skill = MManaWars.m.getActionHandler().getAction("arrowrain_single")!!
      //  skill.loadAsset()

        /**
         * TODO:
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
        army!!.loadedAsset()
        skill.loadedAsset()

        army!!.units.first().produce(100f, GameConstants.CONTROLPANEL_HEIGHT, ControllerTest(), 1).executeAction()
        army!!.units.first().produce(400f, GameConstants.CONTROLPANEL_HEIGHT, ControllerTest(), 1).executeAction()
        army!!.units.first().produce(600f, GameConstants.CONTROLPANEL_HEIGHT, ControllerTest(), 1).executeAction()
        army!!.units.first().produce(800f, GameConstants.CONTROLPANEL_HEIGHT, ControllerTest(), 1).executeAction()


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