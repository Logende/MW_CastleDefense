package org.neubauerfelix.manawars.manawars.entities.animation

import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

interface IBody: ICollidable, IDrawable {

    fun explode()
    fun deadlyHit(killer: IMovable)
    fun deadlyHit()
    fun playEffect(effect: MWAnimationTypeBodyEffect?, weaponType: MWWeaponType?)
    fun update()
    val canFly: Boolean
    val scale: Float

}