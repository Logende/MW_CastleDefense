package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation

interface IAnimatedLiving: IAnimated, ILiving {
    val animation: IEntityAnimation
    fun canFly(): Boolean
}