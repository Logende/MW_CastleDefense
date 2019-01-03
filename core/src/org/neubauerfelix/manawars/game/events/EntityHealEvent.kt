package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class EntityHealEvent(entity: IEntity, val value: Float): EntityEvent(entity){

    var cancelled: Boolean = false

}