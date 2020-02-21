package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars


class CDControllerHuman : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities

    private val buttons: MutableList<IComponent> = arrayListOf()


    override val playerControlled: Boolean
        get() = true

    override fun showControls() {
        val size = GameConstants.CONTROLPANEL_BUTTON_SIZE
        var x = GameConstants.CONTROLPANEL_BUTTON_BORDER
        val y = GameConstants.WORLD_HEIGHT + (GameConstants.CONTROLPANEL_HEIGHT - size) / 2f
        /*for (unit in player.tribe.army.units) {
            val button = unit.generateIcon(x, y, size, size, Runnable {
                if (player.castle.gold >= unit.cost) {
                    player.castle.gold -= unit.cost
                    player.spawnUnit(unit)
                }
            })
            buttons.add(button)
            MManaWars.m.screen.addComponent(button)
            x += GameConstants.CONTROLPANEL_BUTTON_DISTANCE + button.width
        }*/

        for (building in player.tribe.league.buildings) {
            val button = building.generateIcon(x, y, size, size, Runnable {
                if (player.castle.gold >= building.cost) {
                    player.castle.gold -= building.cost
                   // TODO player.spawnUnit(building)
                }
            })
            buttons.add(button)
            MManaWars.m.screen.addComponent(button)
            x += GameConstants.CONTROLPANEL_BUTTON_DISTANCE + button.width
        }
    }

    override fun hideControls() {
        for (button in buttons) {
            MManaWars.m.screen.removeComponent(button)
        }
        buttons.clear()
    }

    override fun doLogic(delta: Float) {
        analysis.update(player)
    }
}