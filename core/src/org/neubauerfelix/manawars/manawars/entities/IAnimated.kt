package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimation
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

interface IAnimated: IDrawable {

    val entityAnimationType: MWEntityAnimationType
}