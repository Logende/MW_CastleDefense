package org.neubauerfelix.manawars.manawars.entities.controller

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class ControllerTest: IController {

    override lateinit var controlled: IControlled

    var dir = 1

    override fun doLogic(delta: Float) {
        if (controlled.x > 1500 || dir == -1 && controlled.x > 200) {
            controlled.walkLeft(200f)
            dir = -1
        }
        if (controlled.x < 200 || dir == 1 && controlled.x < 1500) {
            controlled.walkRight(800f)
            if (dir == -1) {
                controlled.executeAction()
            }
            dir = 1
        }
    }

    override fun drawAbove(delta: Float, batcher: Batch) {
    }

    override fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean {
        return true
    }

    override fun death(damager: IEntity, cause: MWDamageCause): Boolean {
        return true
    }
}