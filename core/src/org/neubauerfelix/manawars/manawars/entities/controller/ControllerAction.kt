package org.neubauerfelix.manawars.manawars.entities.controller

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class ControllerAction: IController {

    override lateinit var controlled: IControlled

    var dir = 1

    override fun doLogic(delta: Float) {
        controlled.executeAction()
        if (controlled.canPerformAction()) {
            controlled.executeAction()
        }
    }

    override fun drawAbove(batcher: Batch) {
    }

    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        return true
    }

    override fun death(damager: IEntity, cause: MWDamageCause) {
    }
}