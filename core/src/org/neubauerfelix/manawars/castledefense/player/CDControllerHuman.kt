package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit


class CDControllerHuman : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities

    private val buttons: MutableList<IComponent> = arrayListOf()


    override val playerControlled: Boolean
        get() = true

    override fun showControls() {
        val size = GameConstants.CONTROLPANEL_BUTTON_SIZE
        var x = GameConstants.CONTROLPANEL_BUTTON_BORDER
        var y = GameConstants.WORLD_HEIGHT + (GameConstants.CONTROLPANEL_HEIGHT - size) / 2f
        for (unit in player.tribe.army.units) {
            val button = CDComponentUnit(x, y, size, size, unit, object: CDComponentUnit.UnitClickListener {
                override fun clickedUnit(unit: IDataUnit) {
                    if (player.castle.gold >= unit.analysis.cost) {
                        player.castle.gold -= unit.analysis.cost
                        player.spawnUnit(unit)
                    }
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