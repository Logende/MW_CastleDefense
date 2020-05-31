package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.components.CDComponentEntity
import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

// Display unit icons. Lists all of them and is able to handle multiple columns of units.
class BoxUnitsInfoIcons(x: Float, y: Float, width: Float, val units: Iterable<IDataUnit>,
                        unitRunnable: (IDataUnit) -> Unit, unitScale: Float) : MComponentContainer(x, y) {


    init {
        val unitWidth = CDConstants.UI_MENU_UNITINFO_ICON_SIZE * unitScale
        val minOffset = MConstants.UI_DISTANCE_SMALL
        // note that this calculation is not exact because the last or first of all units does not need any offset
        val maxUnitsPerColumn = ((width ) / (unitWidth + minOffset) ).toInt()
        val unitWidthSum = unitWidth * maxUnitsPerColumn
        val unitDiff = (width - unitWidthSum) / (maxUnitsPerColumn - 1)
        var unitX = 0f
        var unitY = 0f
        var counter = 0
        for (unit in units) {
            val component = CDComponentUnit(unitX, unitY, unitWidth, unitWidth, unit,
                    Runnable { unitRunnable.invoke(unit) })
            addComponent(component)
            unitX += component.width + unitDiff
            counter++
            if (counter >= maxUnitsPerColumn) {
                unitY += component.height + unitDiff
                unitX = 0f
                counter = 0
            }
        }

    }




}