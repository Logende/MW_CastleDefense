package org.neubauerfelix.manawars.castledefense.player

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.components.CDComponentButtonImage
import org.neubauerfelix.manawars.castledefense.data.IDataLeague
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars


class CDControllerHuman(val league: IDataLeague) : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities

    private val buttons: MutableList<IComponent> = arrayListOf()


    override val playerControlled: Boolean
        get() = true

    override fun showControls() {
        val size = GameConstants.CONTROLPANEL_BUTTON_SIZE
        var x = GameConstants.CONTROLPANEL_BUTTON_BORDER
        val y = GameConstants.WORLD_HEIGHT + (GameConstants.CONTROLPANEL_HEIGHT - size) / 2f

        val buildingButtonTexture = MManaWars.m.getImageHandler().getTextureRegionButton("buildings")
        val buildingButton = CDComponentButtonImage(x, y, size, size, buildingButtonTexture, listener = Runnable {
            hideControls()
            showControlsBuildings()
        })
        x = addButton(buildingButton, x, y, size)

        for (unit in player.tribe.army.units) {
            val button = unit.generateIcon(x, y, size, size, Runnable {
                if (player.castle.gold >= unit.cost) {
                    player.castle.gold -= unit.cost
                    player.spawnUnit(unit)
                }
            })
            buttons.add(button)
            MManaWars.m.screen.addComponent(button)
            x = addButton(button, x, y, size)
        }
    }

    private fun addButton(button: IComponent, x: Float, y: Float, size: Float) : Float {
        buttons.add(button)
        MManaWars.m.screen.addComponent(button)
       return x + GameConstants.CONTROLPANEL_BUTTON_DISTANCE + button.width
    }

    fun showControlsBuildings() {
        val size = GameConstants.CONTROLPANEL_BUTTON_SIZE
        var x = GameConstants.CONTROLPANEL_BUTTON_BORDER
        val y = GameConstants.WORLD_HEIGHT + (GameConstants.CONTROLPANEL_HEIGHT - size) / 2f

        val unitButtonTexture = MManaWars.m.getImageHandler().getTextureRegionButton("units")
        val unitButton = CDComponentButtonImage(x, y, size, size, unitButtonTexture, Color.WHITE, Runnable {
            hideControls()
            showControls() // show unit controls
        })
        x = addButton(unitButton, x, y, size)

        for (building in league.buildings) {
            val button = building.generateIcon(x, y, size, size, Runnable {
                val castle = player.castle
                if (castle.gold >= building.cost) {
                    castle.gold -= building.cost
                    building.produceBuilder(castle.centerHorizontal, player = player)
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