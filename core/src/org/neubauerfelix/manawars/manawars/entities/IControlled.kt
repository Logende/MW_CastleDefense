package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.ILocated
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.controller.IController

interface IControlled: IActionUser, ILiving {


    val data: IDataUnit

    var controller: IController

    val walkSpeedMax: Float
    val walkAcceleration: Float


    var goalX: Float

    /**
     * Tries making the entity jump.
     * @return `true` if the entity was able to jump.
     */
    fun jump(maxJumps: Int): Boolean
    /**
     * Makes the entity look to the direction of the given rectangle.
     * @param l Rectangle to look to.
     */
    fun lookTo(l: GameRectangle)

    /**
     * Makes the entity look to a certain direction.
     * @param directionNew New direction.
     */
    fun lookTo(directionNew: Int)


}