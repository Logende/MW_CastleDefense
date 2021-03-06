package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.IShapeDrawable
import org.neubauerfelix.manawars.manawars.components.MComponentContainer

class CDComponentConditioned(x: Float, y: Float, subComponent: IComponent, private val condition: IPercentageCondition,
                             private val lockRadiusPercentage: Float) :
        MComponentContainer(x, y), IShapeDrawable {

    init {
        addComponent(subComponent)
    }

    override fun draw(shapeRenderer: ShapeRenderer) {
        if (condition.locked) {
            IPercentageCondition.drawProgressbar(shapeRenderer, this.centerLocation,
                    this.height/2f*lockRadiusPercentage,
                    condition, 0L)
        }
    }

    override fun touch(x: Float, y: Float, pointerId: Int): Boolean {
        if (condition.locked) {
            return false
        }
        return super.touch(x, y, pointerId)
    }

    override fun release(x: Float, y: Float, pointerId: Int): Boolean {
        if (condition.locked) {
            return false
        }
        return super.release(x, y, pointerId)
    }
}

