package org.neubauerfelix.manawars.manawars.entities.animation

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

interface IEntityAnimation: IDrawable, ICollidable{


    var paused: Boolean
    val scale: Float
    var color: Color

    fun playDeathAnimation(damager: IEntity?, cause: MWDamageCause)

    fun playBodyEffect(effect: MWAnimationTypeBodyEffect?, weaponType: MWWeaponType?)

    /**
     * Needs to be called, whenever the type of animation requires to be updated.
     * For example when an entity starts/stop moving, jumps, lands on ground, etc.
     * The method automatically reads the current entity state and adapts the animation to it.
     */
    fun update()
}