package org.neubauerfelix.manawars.manawars.handlers

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized

interface IAnimationHandler: IHandler {


    fun playAnimation(animation: Animation<TextureRegion>, x: Float, y: Float, color: Color?): ISized
    fun playAnimationDamaging(animation: Animation<TextureRegion>, x: Float, y: Float, owner: IEntity, damage: Float, variableDamage: Boolean): ISized
    fun playBloodAnimation(x: Float, y: Float, color: Color? = null): ISized
    fun playBloodAnimation(d: IEntity, color: Color? = null): ISized


}