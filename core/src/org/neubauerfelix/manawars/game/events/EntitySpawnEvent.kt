package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class EntitySpawnEvent(entity: IEntity, val respawn: Boolean, var healthPercentage: Float): EntityEvent(entity)