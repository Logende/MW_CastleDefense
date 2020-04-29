package org.neubauerfelix.manawars.game.events

import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars

open class EntityEvent(val entity: IEntity): IEvent {


    override val gametime: Long = MManaWars.m.screen.getGameTime()
}