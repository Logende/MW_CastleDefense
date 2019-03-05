package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.controller.IController

interface IControlled: IActionUser, ILiving {


    val data: IDataUnit

    var controller: IController


    /**
     * Tries making the entity walk to the right.
     * @return `true` if the entity was able to start walking. Does return `false` if the entity was already walking to the right.
     */
    fun walkRight(walkSpeed: Float): Boolean

    /**
     * Tries making the entity walk to the left.
     * @return `true` if the entity was able to start walking. Does return `false` if the entity was already walking to the left.
     */
    fun walkLeft(walkSpeed: Float): Boolean

    /**
     * Tries making the entity stop walking.
     * @return `true` if the was able to stop walking. Does return `false` if the entity was already still.
     */
    fun walkStop(): Boolean

    /**
     * Tries making the entity fly down.
     * @return `true` if the entity was able to start flying down.
     */
    fun flyDown(flySpeed: Float): Boolean

    /**
     * Tries making the entity fly up.
     * @return `true` if the entity was able to start flying up.
     */
    fun flyUp(flySpeed: Float): Boolean

    /**
     * Tries making the entity stop flying up/down.
     * @return `true` if the was able to stop flying up/down. Does return `false` if the entity was already still.
     */
    fun flyStop(): Boolean

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