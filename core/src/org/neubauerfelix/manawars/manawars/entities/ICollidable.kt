package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType


interface ICollidable: ISized {


    fun getCollisionType(other: ISized): MWCollisionType



}
