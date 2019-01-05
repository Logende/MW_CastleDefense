package org.neubauerfelix.manawars.manawars.entities.controller

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IControlled
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

interface IController {

    var controlled: IControlled

    fun doLogic(delta: Float)

    fun drawAbove(delta: Float, batcher: Batch)

    fun damage(value: Float, damager: IEntity, cause: MWDamageCause): Boolean

    fun death(damager: IEntity, cause: MWDamageCause): Boolean

}