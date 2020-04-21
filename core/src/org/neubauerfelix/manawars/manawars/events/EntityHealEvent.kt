package org.neubauerfelix.manawars.manawars.events

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.events.EntityEvent

class EntityHealEvent(entity: IEntity, val value: Float): EntityEvent(entity){

    var cancelled: Boolean = false

}