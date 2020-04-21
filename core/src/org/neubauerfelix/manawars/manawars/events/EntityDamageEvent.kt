package org.neubauerfelix.manawars.manawars.events

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.events.EntityEvent
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class EntityDamageEvent(entity: IEntity, var damage: Float, val damager: IEntity,
                        val cause: MWDamageCause, var deadlyDamage: Boolean): EntityEvent(entity){

    var cancelled: Boolean = false

}