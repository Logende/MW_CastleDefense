package org.neubauerfelix.manawars.manawars.entities.controller

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class ControllerDummy: IController {

    override lateinit var controlled: IControlled


    override fun doLogic(delta: Float) {
    }

    override fun drawAbove(batcher: Batch) {
    }

    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        return true
    }

    override fun death(damager: IEntity, cause: MWDamageCause) {
    }
}