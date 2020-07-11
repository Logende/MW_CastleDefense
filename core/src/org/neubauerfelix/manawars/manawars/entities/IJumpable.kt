package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IMovable

interface IJumpable: IMovable {


    var flying: Boolean


    val jumpsAmount: Int
    val isOnGround: Boolean
    val isKnockbacked: Boolean
    val isFalling: Boolean
    val isJumping: Boolean
    val knockbackFactor: Float



    /**
     * Causes knockback. While being knocked back the entity is unable to walk.
     * Includes the entity scale: Bigger entities suffer less knockback (proportional).
     * If the entity is on ground while knocked back the knockback counts as first jump.
     * @param power_x Knockback in x direction (absolute value!).
     * @param power_y Knockback in y direction.
     * @param source Entity which caused the knockback.
     * @return `true` if it was possible to execute the knockback.
     */
    fun knockback(power_x: Float, power_y: Float, source: IMovable): Boolean

    /**
     * Causes knockback. While being knocked back the entity is unable to walk.
     * Includes the entity scale: Bigger entities suffer less knockback (proportional).
     * If the entity is on ground while knocked back the knockback counts as first jump.
     * @param power_x Knockback in x direction (absolute value!).
     * @param power_y Knockback in y direction.
     * @param direction Horizontal knockback direction.
     * @return `true` if it was possible to execute the knockback.
     */
    fun knockback(power_x: Float, power_y: Float, direction: Int): Boolean

    /**
     * Causes the entity to jump without checking if the entity is physically able to.
     */
    fun jump()

}