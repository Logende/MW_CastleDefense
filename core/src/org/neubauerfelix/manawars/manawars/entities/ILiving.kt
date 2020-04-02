package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

/**
 * ILiving entities have certain health and can be damaged or even killed.
 * @author Felix Neubauer
 */
interface ILiving : ISized, IMovable, ILogicable {

    val invincible: Boolean
    val health: Float
    val healthMax: Float



    /**
     * Deals damage to the entity.
     * @param damage Amount of damage to deal.
     * @param damager Entity who dealt damage (always the direct entity and not the owner of it).
     * @param cause Reason why damage was caused.
     * @return `true` if the damage killed the entity.
     */
    fun damage(damage: Float, damager: IEntity, cause: MWDamageCause): Boolean


    /**
     * Heals the entity by `d` health. Does not exceed the maximum health. Is safe to be called any time (even if the entity has maximum health already).
     * @param value Health to add to the current health of the entity.
     */
    fun heal(value: Float)



}
