package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.CDScreen
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildingAction
import org.neubauerfelix.manawars.game.GameManaWars
import org.neubauerfelix.manawars.manawars.analysis.ISkillStatsHandler
import org.neubauerfelix.manawars.manawars.analysis.SkillStatsHandler
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.factories.IComponentFactory
import org.neubauerfelix.manawars.manawars.factories.MComponentFactory
import org.neubauerfelix.manawars.manawars.handlers.*

open class MManaWars: GameManaWars() {

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
        loadHandler(SkillSetupHandler())
        loadHandler(SkillStatsHandler())
        loadHandler(ActionHandler())
        loadHandler(CollisionHandler())
        loadHandler(BaseUnitHandler())
        loadHandler(UnitHandler())
        loadHandler(CharacterBarHandler())
        startScreen(TestScreenLoad(this), true)
        print("load")
    }


    override fun loadedGame() {
        assert(!loaded)
        print("loaded")

        // can be called to generate new skill analysis file, which can manually be moved to assets folder
        analyseSkills()

        MWState.values().forEach { state -> state.load() }
        startScreen(CDScreen(this), true)
    }

    private fun analyseSkills() {
        val units = CDManaWars.cd.getLeagueHandler().listLeagues().flatMap {
            league -> league.tribes.flatMap { tribe -> tribe.army.units } }
        val buildings = CDManaWars.cd.getLeagueHandler().listLeagues().flatMap {
            league -> league.buildings }
        val actionsUnits = units.map { it.action }.filterIsInstance(IDataSkill::class.java)
        val actionsBuildings = buildings.filterIsInstance(IDataBuildingAction::class.java).map { it.action }
                .filterIsInstance(IDataSkill::class.java)
        val actionsAll = actionsUnits + actionsBuildings
        getSkillStatsHandler().generateStats(MConstants.SKILL_STATS_FILE, actionsAll)
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
        return getHandler(SkillSetupHandler::class.java)
    }

    fun getSkillStatsHandler(): ISkillStatsHandler {
        return getHandler(SkillStatsHandler::class.java)
    }

    fun getActionHandler(): IActionHandler {
        return getHandler(ActionHandler::class.java)
    }

    fun getUnitHandler(): IUnitHandler {
        return getHandler(UnitHandler::class.java)
    }

    fun getCollisionHandler(): ICollisionHandler {
        return getHandler(CollisionHandler::class.java)
    }

    fun getCharacterBarHandler(): ICharacterBarHandler {
        return getHandler(CharacterBarHandler::class.java)
    }

    fun getBaseUnitHandler(): IBaseUnitHandler {
        return getHandler(BaseUnitHandler::class.java)
    }


}