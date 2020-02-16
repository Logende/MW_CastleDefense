package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.controller.IController

interface IControlled: IActionUser, ILiving, IWalkable {


    val data: IDataUnit
    var controller: IController
    var goalX: Float

    /**
     * Tries making the entity jump.
     * @return `true` if the entity was able to jump.
     */
    fun jump(maxJumps: Int): Boolean

}