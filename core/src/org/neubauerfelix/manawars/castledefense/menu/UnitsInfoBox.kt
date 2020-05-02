package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.components.CDComponentEntity
import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class UnitsInfoBox(x: Float, y: Float, width: Float, val units: Iterable<IDataUnit>, buttonView: Boolean,
                   unitRunnable: (IDataUnit) -> Unit) : MComponentContainer(x, y) {


    init {
        if (buttonView) {
            val unitDiff = 5f
            val availableWidth = width - unitDiff * (units.count() -1)
            val buttonSize = availableWidth / units.count()
            var unitX = 0f
            for (unit in units) {
                val component = CDComponentUnit(unitX, 0f, buttonSize, buttonSize, unit,
                        Runnable { unitRunnable.invoke(unit) })
                addComponent(component)
                unitX += component.width + unitDiff
            }

        } else {
            val unitRowHeight = units.map { it.animation.bodyHeight }.max()!!.toFloat()
            val unitWidthSum = units.map { it.animation.bodyWidth }.sum().toFloat()
            val unitDiff = (width - unitWidthSum) / (units.count() - 1)
            var unitX = 0f
            for (unit in units) {
                val unitWidth = unit.animation.bodyWidth.toFloat()
                val unitHeight = unit.animation.bodyHeight.toFloat()
                val yOffset = unitRowHeight - unitHeight
                val animation = unit.animation.produce(unitX, yOffset, unitWidth, unitHeight)
                val component = CDComponentEntity(animation, Runnable { unitRunnable.invoke(unit) })
                addComponent(component)
                unitX += component.width + unitDiff
            }
        }
    }




}