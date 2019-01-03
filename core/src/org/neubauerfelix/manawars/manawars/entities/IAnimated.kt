package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation

interface IAnimated: ILiving, IDrawable {
    val animation: IEntityAnimation
}