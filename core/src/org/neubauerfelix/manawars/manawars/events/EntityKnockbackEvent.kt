package org.neubauerfelix.manawars.manawars.events

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.events.EntityEvent

class EntityKnockbackEvent(entity: IEntity, val powerX: Float, val powerY: Float): EntityEvent(entity)