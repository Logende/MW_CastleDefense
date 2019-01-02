package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

/**
 * Implemented by entities capable of dealing damage to other entities.
 *
 * @author Felix Neubauer
 */
interface IAttacking : ISized {

    /**
     * Automatically executed when the entity dealt damage to an other entity.
     * @param victim Entity that was damaged.
     * @param damage Amount of damage dealt.
     * @param cause Damage cause.
     */
    fun dealtDamage(victim: ILiving, damage: Float, cause: MWDamageCause)

    /**
     * Automatically executed when the entity killed an other entity.
     * @param victim Entity that was killed.
     * @param cause Kill cause.
     */
    fun killed(victim: ILiving, cause: MWDamageCause)

}
