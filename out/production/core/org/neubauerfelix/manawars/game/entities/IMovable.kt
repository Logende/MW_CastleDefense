package org.neubauerfelix.manawars.game.entities

/**
 * IMovable objects are capable of moving. This interface makes sure they provide a method to update their movement.
 * @author Felix Neubauer
 */
interface IMovable: IEntity {

    var speedX: Float
    var speedY: Float
    var accelerationX: Float
    var accelerationY: Float

    val speedDirectionVertical: Int
    val speedDirectionHorizontal: Int

    /**
     * Movement update. Automatically executed on a regular basis (every game tick).
     * @param delta time difference from latest call.
     */
    fun move(delta: Float)

}
