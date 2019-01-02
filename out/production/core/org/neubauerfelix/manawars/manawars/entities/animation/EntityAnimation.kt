package org.neubauerfelix.manawars.manawars.entities.animation

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized

abstract class EntityAnimation(var sized: ISized, scale: Float) : IEntityAnimation, ISized by sized, IEntity{

    override var paused: Boolean = false
    override val scale: Float = scale
    override var color: Color = Color.WHITE

    override var remove: Boolean = false
    override var propertyScale: Float = scale

    override fun spawn() {
        AManaWars.m.screen.addEntity(this)
    }
}