package org.neubauerfelix.manawars.manawars.entities.animation

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.game.entities.IMovable
import org.neubauerfelix.manawars.game.entities.ISized
import org.neubauerfelix.manawars.manawars.entities.ICollidable
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

interface IBody: ICollidable, IDrawable, ILogicable {

    fun explode()
    fun deadlyHit(killer: IMovable)
    fun deadlyHit()
    fun playEffect(effect: MWAnimationTypeBodyEffect?, weaponType: MWWeaponType?)
    fun drawDebugging(shapeRenderer: ShapeRenderer)

    /** Sized is used to determine the animation type. It can be different from the actual ISized instance behind
     * the body. Size and position of the given sized instance are not used.
     */
    fun updateAnimation(sized: ISized?)

    val canFly: Boolean
    val scale: Float
    val playingBodyEffect: Boolean

}