package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.game.entities.IEntity

class EntityKnockbackEvent(entity: IEntity, val powerX: Float, val powerY: Float): EntityEvent(entity)