package org.neubauerfelix.manawars.manawars.entities

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.entities.properties.IEntityProperty
import org.neubauerfelix.manawars.manawars.enums.MWDamageCause

class MEntityComposed(animationProducer: IEntityAnimationProducer, health: Float, var name: String?) : MEntityAnimated(animationProducer, health), IComposed {

    private val properties = ArrayList<IEntityProperty>()


    fun addProperty(property: IEntityProperty): MEntityComposed {
        property.init(this)
        properties.add(property)
        return this
    }

    override fun hasProperty(c: Class<*>): Boolean {
        synchronized(properties) {
            for (property in properties) {
                if (c.isInstance(property)) {
                    return true
                }
            }
        }
        return false
    }

    override fun <T> getProperty(c: Class<T>): T? {
        synchronized(properties) {
            for (property in properties) {
                if (c.isInstance(property)) {
                    return c.cast(property)
                }
            }
        }
        return null
    }

    fun pasteComposed(e: MEntityComposed, onlyTemporaryValues: Boolean) {
        super.pasteJumpable(e, onlyTemporaryValues)
        synchronized(properties) {
            for (propertyOld in e.properties) {
                val propertyNew = getProperty(propertyOld::class.java)
                        ?: throw RuntimeException("Can not paste entity '${e.name}' into entity '$name': The entity to paste data into is missing the property '${propertyOld::class.java.name}'.")
                propertyNew.paste(propertyOld)
            }
        }
    }

    override fun spawn() {
        //TODO: Call event
        super.spawn()
    }

    override fun doLogic(delta: Float) {
        synchronized(properties) {
            for (property in properties) {
                property.doLogic(delta)
            }
        }
        super.doLogic(delta)
    }

    fun draw(batcher: Batch, delta: Float) {
        super.draw(delta, batcher)
        synchronized(properties) {
            for (property in properties) {
                property.drawAbove(batcher, delta)
            }
        }
    }

    fun damage(d: Double, damager: GameEntity, cause: MWDamageCause): Boolean {
        //TODO: Call event
        return super.damage(d.toFloat(), damager, cause)
    }

    fun death(damager: GameEntity, cause: MWDamageCause): Boolean {
        val dies = super.death(damager, cause)
        //TODO: Call event
        return dies
    }

    fun dealtDamage(l: ILiving, damage: Double, reason: MWDamageCause) {
        //TODO: Call event
        super.dealtDamage(l, damage.toFloat(), reason)
    }

    override fun knockback(power_x: Float, power_y: Float): Boolean {
        //TODO: Call event
        return super.knockback(power_x, power_y)
    }



}
