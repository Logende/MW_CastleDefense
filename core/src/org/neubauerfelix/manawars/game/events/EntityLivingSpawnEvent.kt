package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.manawars.entities.ILiving

class EntityLivingSpawnEvent(entity: ILiving, val respawn: Boolean, var healthPercentage: Float): EntityEvent(entity)