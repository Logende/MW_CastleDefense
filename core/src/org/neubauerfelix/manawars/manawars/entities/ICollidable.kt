package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWCollisionType


interface ICollidable: ISized {

    companion object {

        //used as temporary variable whenever an intersection is calculated in the +checkCollision method
        private var intersection = GameRectangle(0f, 0f)

        fun checkCollision(a: ICollidable, b: ICollidable): Array<MWCollisionType>?{
            //1. Check whether rectangles overlap
            if(!a.overlaps(b)){
                return null
            }

            //2. Determine intersection
            a.getIntersection(b, intersection)

            //3. Return collision types
            return arrayOf(a.getCollisionType(intersection), b.getCollisionType(intersection))
        }
    }


    fun getCollisionType(intersection: ISized): MWCollisionType



}
