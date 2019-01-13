package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType


interface ICollidable: ISized {

    companion object {

        fun checkCollision(a: ICollidable, b: ICollidable): Array<MWCollisionType>?{
            if(!a.overlaps(b)){
                return null
            }
            //3. Return collision types
            return arrayOf(a.getCollisionType(b), b.getCollisionType(a))
        }
    }


    fun getCollisionType(other: ISized): MWCollisionType



}
