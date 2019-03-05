package org.neubauerfelix.manawars.castledefense.player

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.IControlled

class CDFormation(startAnchorX: Float, private val units: List<IDataUnit>, private val castleDirection: Int) {


    var rangeBest = 0f // range which enables most units to use their action
    var rangeFirst = 0f // range which enables at least one unit to use their action

    val anchorX: Float = startAnchorX
    val entities: MutableList<IControlled> = arrayListOf()
    val relativeX: MutableMap<IControlled, Float> = hashMapOf()


    fun addEntity(e: IControlled) {
        synchronized(entities) {
            synchronized(relativeX) {
                entities.add(e)
                updateFormation()
            }
        }
    }

    fun removeEntity(e: IControlled) {
        synchronized(entities) {
            synchronized(relativeX) {
                entities.remove(e)
                this.relativeX.remove(e)
                updateFormation()
            }
        }
    }

    fun getAssignedX(e: IControlled): Float {
        require(entities.contains(e))
        return anchorX + relativeX[e]!!
    }

    fun updateFormation() {
        // 1. Simply sort the units in the right order
        entities.sortedBy { s ->
            val priority = this.units.indexOf(s.data)
            priority * 10000 + s.getDistanceHor(anchorX)
        }

        // 2. Assign locations to each unit
        var x = 0f
        for (e in this.entities) {
            this.relativeX[e] = if (castleDirection == 1) {
                x - e.width
            } else {
                x
            }
            x += if (castleDirection == 1) {
                - e.width - CDConstants.CASTLE_BORDER_OFFSET
            } else {
                e.width + CDConstants.CASTLE_BORDER_OFFSET
            }
         }

        rangeFirst = 0f
        rangeBest = Float.MAX_VALUE
        // 3. Upgrade formation ranges
        relativeX.forEach { e, x ->
            val range = e.data.action.getActionProperties(e.entityAnimationType).rangeMaxAvg - e.getDistanceHor(anchorX)
            rangeFirst = Math.max(rangeFirst, range)
            rangeBest = Math.min(rangeBest, range)
        }
    }


}