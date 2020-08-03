package org.neubauerfelix.manawars.castledefense.player

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.analysis.CDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.analysis.ICDPlayerLiveAnalysis
import org.neubauerfelix.manawars.castledefense.components.CDComponentButtonImage
import org.neubauerfelix.manawars.castledefense.components.CDComponentConditioned
import org.neubauerfelix.manawars.castledefense.components.CDComponentScheduledUnit
import org.neubauerfelix.manawars.castledefense.components.IPercentageCondition
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.GameLocation
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import kotlin.math.min


class CDControllerHuman() : ICDController {


    companion object {

        fun getScheduledActionButtonPosition(index: Int): ILocated {
            val horMax = CDConstants.UI_SCHEDULED_UNIT_ICON_HOR_MAX
            val horMin = CDConstants.UI_SCHEDULED_UNIT_ICON_HOR_MIN
            val verMin = CDConstants.UI_SCHEDULED_UNIT_ICON_VER_MIN
            val buttonSize = CDConstants.UI_SCHEDULED_UNIT_ICON_SIZE
            val horDist = CDConstants.UI_SCHEDULED_UNIT_ICON_HOR_DIST
            val verDist = CDConstants.UI_SCHEDULED_UNIT_ICON_VER_DIST
            val horLength = horMax - horMin
            val buttonsPerRow = ((horLength + 1* horDist)  / (buttonSize + horDist)).toInt()
            val row = index / buttonsPerRow
            val column = index % buttonsPerRow
            return GameLocation(horMin + column * (buttonSize + horDist), verMin + row * (buttonSize + verDist))
        }
    }


    override lateinit var player: ICDPlayer
    override val analysis: ICDPlayerLiveAnalysis = CDPlayerLiveAnalysis() // analysis of own entities

    private val buttons: MutableList<IComponent> = arrayListOf()

    private val scheduledActions: MutableList<IComponent> = arrayListOf()


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
                // TODO: display currently ordered units in GUI
                if (player.castle.storedMoney >= unit.cost) {
                    player.orderUnitToBuild(unit)
                    updateScheduledActionButtons()
                }
            })
            val buttonConditioned = CDComponentConditioned(x, y, button, object : IPercentageCondition{
                override val needed: Float
                    get() = unit.cost.toFloat()
                override val available: Float
                    get() = min(player.castle.storedMoney,
                            player.castle.unitCostPerCycle - player.unitsToBuildNextCycle.sumBy { it.cost }).
                            toFloat()
            }, 0.8f)
            x = addButton(buttonConditioned, x)
        }
    }


    private fun addButton(button: IComponent, x: Float) : Float {
        buttons.add(button)
        MManaWars.m.screen.addComponent(button)
        return x + GameConstants.CONTROLPANEL_BUTTON_DISTANCE + button.width
    }

    private fun addScheduledActionButton(unit: IDataUnit, scheduledActionsCount: Int, listener: Runnable) {
        val index = scheduledActions.size
        val pos = getScheduledActionButtonPosition((scheduledActionsCount - 1) - index)
        val button = CDComponentScheduledUnit(pos.x, pos.y, CDConstants.UI_SCHEDULED_UNIT_ICON_SIZE,
                CDConstants.UI_SCHEDULED_UNIT_ICON_SIZE, unit, listener)
        scheduledActions.add(button)
        MManaWars.m.screen.addComponent(button)
    }

    private fun updateScheduledActionButtons() {
        // Step 1: remove all component elements
        val screen = MManaWars.m.screen
        for (c in scheduledActions) {
            screen.removeComponent(c)
        }
        scheduledActions.clear()

        // Step 2. re-add all elements that still exist / exist now
        for ((index, unit) in player.unitsToBuildNextCycle.withIndex()) {
            addScheduledActionButton(unit, player.unitsToBuildNextCycle.size, Runnable {
                player.cancelUnitToBuild(index)
                updateScheduledActionButtons()
            })
        }
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
                // TODO
                //if (castle.gold >= building.cost) {
                //   castle.gold -= building.cost
                //  building.produceBuilder(castle.centerHorizontal, player = player)
                // }
            })
            val buttonConditioned = CDComponentConditioned(x, y, button, object : IPercentageCondition{
                override val needed: Float
                    get() = building.cost.toFloat()
                override val available: Float
                    get() = 0f // todo player.castle.gold.toFloat()
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


    override fun load() {
        analysis.load()
    }

    override fun dispose() {
        hideControls()
        analysis.dispose()
    }

    override fun executedUnitBuilding() {
        updateScheduledActionButtons()
    }
}