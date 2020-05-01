package org.neubauerfelix.manawars.manawars.components

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.ILogicable

import java.util.ArrayList

open class MComponentContainer(x: Float, y: Float) : MComponent(x, y, 0f, 0f), IComponentContainer,
        ILogicable{

    private val components = ArrayList<IComponent>()

    override fun draw(batcher: Batch, offsetX: Float, offsetY: Float) {
        //batcher.draw(GameImageHandler.imageHandler!!.getTextureRegionButton("frame.skill.shield"), offsetX + x, offsetY + y, width, height)
        synchronized(components) {
            for (i in components.indices.reversed()) {
                val c = components[i]
                if (!c.isHidden()) {
                    c.draw(batcher, offsetX + x, offsetY + y)
                }
            }
        }
    }

    override fun touch(x: Float, y: Float, pointerId: Int): Boolean {
        if(super.touch(x, y, pointerId)){
            val translatedX = x - this.x
            val translatedY = y - this.y
            synchronized(components) {
                for (i in components.indices.reversed()) {
                    val c = components[i]
                    if (!c.isHidden()) {
                        if (c.touch(translatedX, translatedY, pointerId)) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    override fun release(x: Float, y: Float, pointerId: Int): Boolean {
        if(super.release(x, y, pointerId)){
            val translatedX = x - this.x
            val translatedY = y - this.y
            synchronized(components) {
                for (i in components.indices.reversed()) {
                    val c = components[i]
                    if (!c.isHidden()) {
                        if (c.release(translatedX, translatedY, pointerId)) {
                            return true
                        }
                    }
                }
            }
        }
        return false
    }

    override fun clickAction() {
    }

    override fun unclickAction() {
    }

    override fun addComponent(component: IComponent, above: Boolean) {
        if(above) {
            components.add(component)
        }else{
            components.add(0, component)
        }
        setSize(Math.max(component.x + component.width , width), Math.max(component.y + component.height, height))
    }

    override fun removeComponent(component: IComponent) {
        components.remove(component)
    }

    override fun clearComponents() {
        components.clear()
    }

    override fun doLogic(delta: Float) {
        synchronized(components) {
            for (i in components.indices.reversed()) {
                val c = components[i]
                if (!c.isHidden()) {
                    if (c is ILogicable) {
                        c.doLogic(delta)
                    }
                }
            }
        }
    }
}
