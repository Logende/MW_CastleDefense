package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class EntityDeathEvent(entity: IEntity, val damager: IEntity, val cause: MWDamageCause): EntityEvent(entity) {

    var cancelled: Boolean = false
}