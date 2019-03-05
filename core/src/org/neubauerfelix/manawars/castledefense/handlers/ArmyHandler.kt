package org.neubauerfelix.manawars.castledefense.handlers

import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.castledefense.data.DataArmyLoaded
import org.neubauerfelix.manawars.castledefense.data.IDataArmy
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class ArmyHandler : IArmyHandler, ILoadableContent {

    private val armies = java.util.HashMap<String, IDataArmy>()
    override var loadedContent: Boolean = false

    override fun putArmy(army: IDataArmy) {
        armies[army.name] = army
        System.out.println("put army")
    }

    override fun getArmy(name: String): IDataArmy? {
        return armies[name]
    }

    override fun listArmies(): Iterable<IDataArmy> {
        return armies.values
    }


    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true

            val handlerConfigNames = gameConfig.getStringList("armies")
            for (handlerConfigName in handlerConfigNames) {
                val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load("content/$handlerConfigName", true)
                this.loadArmy(handlerConfig)
            }
        }
    }


    private fun loadArmy(config: Configuration) {
        val army = DataArmyLoaded(config)
        army.units.forEach { unit ->
            MManaWars.m.getUnitHandler().putUnit(unit)
        }
        this.putArmy(army)
    }
}