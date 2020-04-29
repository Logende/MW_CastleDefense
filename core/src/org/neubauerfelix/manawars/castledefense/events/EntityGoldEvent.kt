package org.neubauerfelix.manawars.castledefense.events

import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.events.EntityEvent
import org.neubauerfelix.manawars.manawars.MManaWars

/**
 * entity is the entity which earns/looses gold. This is usually the same as castle, but for example in the case of
 * a gold mine, the mine is the entity earning the gold and the castle is listed here too, because it is the entity
 * which holds the gold.
 */
class EntityGoldEvent(entity: IEntity, val castle: ICDEntityCastle, var goldDifference: Int): EntityEvent(entity){

    var cancelled: Boolean = false

}