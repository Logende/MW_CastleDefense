package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.CDScreen
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuildingAction
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.GameManaWars
import org.neubauerfelix.manawars.manawars.analysis.ISkillStatsHandler
import org.neubauerfelix.manawars.manawars.analysis.SkillStatsHandler
import org.neubauerfelix.manawars.manawars.data.units.IDataActionUser
import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.factories.IComponentFactory
import org.neubauerfelix.manawars.manawars.factories.MComponentFactory
import org.neubauerfelix.manawars.manawars.handlers.MusicHandlerMock
import org.neubauerfelix.manawars.manawars.handlers.SoundHandlerMock
import org.neubauerfelix.manawars.manawars.handlers.*

open class MManaWars: GameManaWars() {

    companion object {
        lateinit var m: MManaWars
    }


    init {
        m = this
    }

    private var loaded: Boolean = false

    override fun loadGame() {
        loadHandler(FontHandler(), FontHandler::class.java)
        loadHandler(MComponentFactory(getImageHandler()), IComponentFactory::class.java)
        loadHandler(ShieldHandler(), IShieldHandler::class.java)
        loadHandler(BodyDataHandler(), IBodyDataHandler::class.java)
        loadHandler(AnimationHandler(), IAnimationHandler::class.java)
        loadHandler(LanguageHandler("english"), ILanguageHandler::class.java) //TODO: Load language from config
        loadHandler(UpgradeHandler(), UpgradeHandler::class.java)
        loadHandler(SkillSetupHandler(), ISkillSetupHandler::class.java)
        loadHandler(SkillStatsHandler(), ISkillStatsHandler::class.java)
        loadHandler(ActionHandler(), IActionHandler::class.java)
        loadHandler(CollisionHandler(), ICollisionHandler::class.java)
        loadHandler(BaseUnitHandler(), IBaseUnitHandler::class.java)
        loadHandler(UnitHandler(), IUnitHandler::class.java)
        loadHandler(CharacterBarHandler(), ICharacterBarHandler::class.java)
        loadHandler(BackgroundListHandler(), IBackgroundListHandler::class.java)
        loadHandler(BackgroundComposer(), IBackgroundComposer::class.java)
        startScreen(TestScreenLoad(this), true)

        if (GameConstants.FAST_MODE) {
            loadHandler(SoundHandlerMock(), ISoundHandler::class.java)
            loadHandler(MusicHandlerMock(), IMusicHandler::class.java)
        } else {
            loadHandler(SoundHandler(), ISoundHandler::class.java)
            loadHandler(MusicHandler(), IMusicHandler::class.java)
            loadHandler(TextVisualizationHandler(), TextVisualizationHandler::class.java)
        }
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
        val units = CDManaWars.cd.getUnitHandler().listUnits()
        val buildings = CDManaWars.cd.getBuildingListHandler().buildings.values.
                filterIsInstance(IDataBuildingAction::class.java)
        val allActionUsers : Collection<IDataActionUser> = units + buildings
        getSkillStatsHandler().analyseSkills(allActionUsers)
    }


    override fun isLoaded(): Boolean {
        return loaded
    }

    fun getFontHandler(): FontHandler{
        return getHandler(FontHandler::class.java)
    }

    fun getComponentFactory(): IComponentFactory{
        return getHandler(IComponentFactory::class.java)
    }

    fun getShieldHandler(): IShieldHandler {
        return getHandler(IShieldHandler::class.java)
    }

    fun getBodyDataHandler(): IBodyDataHandler {
        return getHandler(IBodyDataHandler::class.java)
    }

    fun getAnimationHandler(): IAnimationHandler {
        return getHandler(IAnimationHandler::class.java)
    }

    fun getLanguageHandler(): ILanguageHandler {
        return getHandler(ILanguageHandler::class.java)
    }

    fun getSkillSetupHandler(): ISkillSetupHandler {
        return getHandler(ISkillSetupHandler::class.java)
    }

    fun getSkillStatsHandler(): ISkillStatsHandler {
        return getHandler(ISkillStatsHandler::class.java)
    }

    fun getActionHandler(): IActionHandler {
        return getHandler(IActionHandler::class.java)
    }

    fun getUnitHandler(): IUnitHandler {
        return getHandler(IUnitHandler::class.java)
    }

    fun getCollisionHandler(): ICollisionHandler {
        return getHandler(ICollisionHandler::class.java)
    }

    fun getCharacterBarHandler(): ICharacterBarHandler {
        return getHandler(ICharacterBarHandler::class.java)
    }

    fun getBaseUnitHandler(): IBaseUnitHandler {
        return getHandler(IBaseUnitHandler::class.java)
    }

    fun getBackgroundListHandler(): IBackgroundListHandler {
        return getHandler(IBackgroundListHandler::class.java)
    }

    fun getBackgroundComposer(): IBackgroundComposer {
        return getHandler(IBackgroundComposer::class.java)
    }

    fun getSoundHandler(): ISoundHandler {
        return getHandler(ISoundHandler::class.java)
    }

    fun getMusicHandler(): IMusicHandler {
        return getHandler(IMusicHandler::class.java)
    }



}