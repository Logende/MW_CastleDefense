package org.neubauerfelix.manawars.castledefense.player

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.components.CDComponentButtonImage
import org.neubauerfelix.manawars.castledefense.components.CDComponentConditioned
import org.neubauerfelix.manawars.castledefense.components.IPercentageCondition
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars


class CDControllerHuman() : ICDController {

    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities

    private val buttons: MutableList<IComponent> = arrayListOf()


    override val playerControlled: Boolean
        get() = true

    override fun showControls() {
        val size = GameConstants.CONTROLPANEL_BUTTON_SIZE
        var x = GameConstants.CONTROLPANEL_BUTTON_BORDER
        val y = GameConstants.CONTROLPANEL_Y + (GameConstants.CONTROLPANEL_HEIGHT - size) / 2f

        val buildingButtonTexture = MManaWars.m.getImageHandler().getTextureRegionButton("buildings")
        val buildingButton = CDComponentButtonImage(x, y, size, size, buildingButtonTexture, listener = Runnable {
            hideControls()
            showControlsBuildings()
        })
        x = addButton(buildingButton, x)

        for (unit in player.tribe.army.units) {
            val button = unit.generateIcon(0f, 0f, size, size, Runnable {
                if (player.castle.gold >= unit.cost) {
                    player.castle.gold -= unit.cost
                    player.spawnUnit(unit)
                }
            })
            val buttonConditioned = CDComponentConditioned(x, y, button, object : IPercentageCondition{
                override val needed: Float
                    get() = unit.cost.toFloat()
                override val available: Float
                    get() = player.castle.gold.toFloat()
            }, 0.8f)
            x = addButton(buttonConditioned, x)
        }
    }


    private fun addButton(button: IComponent, x: Float) : Float {
        buttons.add(button)
        MManaWars.m.screen.addComponent(button)
       return x + GameConstants.CONTROLPANEL_BUTTON_DISTANCE + button.width
    }

    fun showControlsBuildings() {
        val size = GameConstants.CONTROLPANEL_BUTTON_SIZE
        var x = GameConstants.CONTROLPANEL_BUTTON_BORDER
        val y = GameConstants.CONTROLPANEL_Y + (GameConstants.CONTROLPANEL_HEIGHT - size) / 2f

        val unitButtonTexture = MManaWars.m.getImageHandler().getTextureRegionButton("units")
        val unitButton = CDComponentButtonImage(x, y, size, size, unitButtonTexture, Color.WHITE, Runnable {
            hideControls()
            showControls() // show unit controls
        })
        x = addButton(unitButton, x)

        for (building in player.tribe.buildings) {
            val button = building.generateIcon(0f, 0f, size, size, Runnable {
                val castle = player.castle
                if (castle.gold >= building.cost) {
                    castle.gold -= building.cost
                    building.produceBuilder(castle.centerHorizontal, player = player)
                }
            })
            val buttonConditioned = CDComponentConditioned(x, y, button, object : IPercentageCondition{
                override val needed: Float
                    get() = building.cost.toFloat()
                override val available: Float
                    get() = player.castle.gold.toFloat()
            }, 0.8f)
            x = addButton(buttonConditioned, x)
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