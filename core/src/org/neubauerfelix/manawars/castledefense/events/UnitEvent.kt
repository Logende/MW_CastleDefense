package org.neubauerfelix.manawars.castledefense.events

import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.game.events.IEvent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.DataUnit

open class UnitEvent(val unit: DataUnit, val castle: ICDEntityCastle): IEvent {


    override val gametime: Long = MManaWars.m.screen.getGameTime()
}