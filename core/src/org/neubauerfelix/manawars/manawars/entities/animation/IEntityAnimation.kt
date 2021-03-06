package org.neubauerfelix.manawars.manawars.entities.animation

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

interface IEntityAnimation: IDrawable, ILogicable, ICollidable{


    var paused: Boolean
    val scale: Float
    var color: Color
    val entityAnimationType: MWEntityAnimationType

    val canFly: Boolean

    val playingBodyEffect: Boolean

    fun playDeathAnimation(damager: IEntity?, cause: MWDamageCause)
    fun drawDebugging(shapeRenderer: ShapeRenderer)

    fun playBodyEffect(effect: MWAnimationTypeBodyEffect?)
    fun equipWeapon(weaponType: MWWeaponType)

    /**
     * Needs to be called, whenever the type of animation requires to be updated.
     * For example when an entity starts/stop moving, jumps, lands on ground, etc.
     * The method automatically reads the current entity state and adapts the animation to it.
     */
    fun updateAnimation(sized: ISized?)
}