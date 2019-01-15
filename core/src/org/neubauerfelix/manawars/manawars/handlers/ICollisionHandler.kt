package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.game.entities.IEntity

interface ICollisionHandler: IHandler {


    fun updateCollisions(entities: List<IEntity>)


}