package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.game.GameManaWars
import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.factories.IComponentFactory
import org.neubauerfelix.manawars.manawars.factories.MComponentFactory
import org.neubauerfelix.manawars.manawars.handlers.*

class MManaWars: GameManaWars() {

    companion object {
        lateinit var m: MManaWars
    }


    init {
        MManaWars.m = this
    }

    private var loaded: Boolean = false

    override fun loadGame() {
        loadHandler(FontHandler())
        loadHandler(MComponentFactory(getImageHandler()))
        loadHandler(ShieldHandler())
        loadHandler(BodyDataHandler())
        loadHandler(AnimationHandler())
        loadHandler(LanguageHandler("english")) //TODO: Load language from config
        loadHandler(UpgradeHandler())
        loadHandler(SkillHandler())
        loadHandler(ActionHandler())
        loadHandler(CollisionHandler())
        startScreen(TestScreenLoad(this), true)
        print("load")
    }


    override fun loadedGame() {
        assert(!loaded)
        print("loaded")
        MWState.values().forEach { state -> state.load() }
        startScreen(TestScreen(this), true)
    }

    override fun isLoaded(): Boolean {
        return loaded
    }

    fun getFontHandler(): FontHandler{
        return getHandler(FontHandler::class.java)
    }

    fun getComponentFactory(): IComponentFactory{
        return getHandler(MComponentFactory::class.java)
    }

    fun getShieldHandler(): IShieldHandler {
        return getHandler(ShieldHandler::class.java)
    }

    fun getBodyDataHandler(): IBodyDataHandler {
        return getHandler(BodyDataHandler::class.java)
    }

    fun getAnimationHandler(): IAnimationHandler {
        return getHandler(AnimationHandler::class.java)
    }

    fun getLanguageHandler(): ILanguageHandler {
        return getHandler(LanguageHandler::class.java)
    }

    fun getSkillSetupHandler(): ISkillSetupHandler {
        return getHandler(SkillHandler::class.java)
    }

    fun getSkillAnalysisHandler(): ISkillAnalysisHandler {
        return getHandler(SkillHandler::class.java)
    }

    fun getActionHandler(): IActionHandler {
        return getHandler(ActionHandler::class.java)
    }

    fun getCollisionHandler(): ICollisionHandler {
        return getHandler(CollisionHandler::class.java)
    }


}