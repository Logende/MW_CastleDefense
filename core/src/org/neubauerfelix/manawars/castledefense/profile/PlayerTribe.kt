package org.neubauerfelix.manawars.castledefense.profile

import org.neubauerfelix.manawars.castledefense.data.IDataPlayground
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundSubtheme
import org.neubauerfelix.manawars.manawars.enums.MWBackgroundTheme
import org.neubauerfelix.manawars.manawars.enums.MWUnitType
import org.neubauerfelix.manawars.manawars.storage.Configuration

class PlayerTribe(config: Configuration) : IDataTribe {

    override val name: String = "player"
    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("tribe_${name}_name")

    init {
        println("Loading tribe $name.")
    }


    override val castle = PlayerCastle()
    override val army = PlayerArmy(config, this)

    fun replaceUnit(unitType: MWUnitType, tribe: IDataTribe) {
        army.replaceUnit(unitType, tribe)
        updateBackgroundTheme()
    }


    override lateinit var backgroundTheme: MWBackgroundTheme

    init {
        updateBackgroundTheme()
    }

    private fun updateBackgroundTheme() {
        val summedThemes = army.unitTribes.map { it.backgroundTheme }
        val mostPopular = MWBackgroundTheme.values().maxBy { theme ->
            summedThemes.filter { it == theme }.count()
        }!!
        backgroundTheme = mostPopular
    }

    override val backgroundSubthemes: List<MWBackgroundSubtheme> = MWBackgroundSubtheme.values().toList()

    // TODO: adjust
    override val musicTrack: String = "demetrians_theme"
    override val playground: IDataPlayground
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val buildings: List<IDataBuilding> = arrayListOf()

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(x: Float, y: Float, width: Float, height: Float, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}