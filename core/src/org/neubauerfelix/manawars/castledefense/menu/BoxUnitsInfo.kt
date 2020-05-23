package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.components.CDComponentEntity
import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class BoxUnitsInfo(x: Float, y: Float, width: Float, val units: Iterable<IDataUnit>, buttonView: Boolean,
                   unitRunnable: (IDataUnit) -> Unit, unitScale: Float) : MComponentContainer(x, y) {


    init {
        if (buttonView) {
            val unitWidth = CDConstants.UI_MENU_UNITINFO_ICON_SIZE * unitScale
            val unitWidthSum = unitWidth * units.count()
            val unitDiff = (width - unitWidthSum) / (units.count() - 1)
            var unitX = 0f
            for (unit in units) {
                val component = CDComponentUnit(unitX, 0f, unitWidth, unitWidth, unit,
                        Runnable { unitRunnable.invoke(unit) })
                addComponent(component)
                unitX += component.width + unitDiff
            }

        } else {
            val unitRowHeight = units.map { it.animation.bodyHeight * unitScale }.max()!!.toFloat()
            val unitWidthSum = units.map { it.animation.bodyWidth * unitScale }.sum()
            val unitDiff = (width - unitWidthSum) / (units.count() - 1)
            var unitX = 0f
            for (unit in units) {
                val unitWidth = unit.animation.bodyWidth.toFloat() * unitScale
                val unitHeight = unit.animation.bodyHeight.toFloat() * unitScale
                val yOffset = unitRowHeight - unitHeight
                val animation = unit.animation.produce(unitX, yOffset, unitWidth, unitHeight)
                val component = CDComponentEntity(animation, Runnable { unitRunnable.invoke(unit) })
                addComponent(component)
                unitX += component.width + unitDiff
            }
        }
    }




}