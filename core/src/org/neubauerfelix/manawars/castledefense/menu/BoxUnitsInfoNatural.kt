package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.components.CDComponentEntity
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

// Shows unit animations and not unit icons. Puts all units into one single column and centers them.
class BoxUnitsInfoNatural(x: Float, y: Float, width: Float, val units: Iterable<IDataUnit>,
                          unitRunnable: (IDataUnit) -> Unit, unitScale: Float) : MComponentContainer(x, y) {


    init {
        val unitRowHeight = units.map { it.animation.bodyHeight * unitScale * it.animation.defaultScale }.max()!!.toFloat()
        val unitWidthSum = units.map { it.animation.bodyWidth * unitScale * it.animation.defaultScale }.sum()
        val unitDiff = (width - unitWidthSum) / (units.count() - 1)
        var unitX = 0f
        for (unit in units) {
            val unitWidth = unit.animation.bodyWidth.toFloat() * unitScale * unit.animation.defaultScale
            val unitHeight = unit.animation.bodyHeight.toFloat() * unitScale * unit.animation.defaultScale
            val yOffset = unitRowHeight - unitHeight
            val animation = unit.animation.produce(unitX, yOffset, unitWidth, unitHeight, unit.action.weaponType)
            val component = CDComponentEntity(animation, Runnable { unitRunnable.invoke(unit) })
            addComponent(component)
            unitX += component.width + unitDiff
        }
    }




}