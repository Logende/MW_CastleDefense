package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.components.MComponent
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

open class CDComponentEntity(x: Float, y: Float, width: Float, height: Float, val animation: IEntity,
                             val runnable: Runnable) :
        MComponent(x, y, width, height), ILogicable {

    constructor(animation: IEntity, runnable: Runnable) :
            this(animation.x, animation.y, animation.width, animation.height, animation, runnable)

    constructor(x: Float, y: Float, width: Float, height: Float, animationProducer: IEntityAnimationProducer,
                runnable: Runnable, weaponType: MWWeaponType?) :
            this(x, y, width, height, animationProducer.produce(x, y, width, height, weaponType), runnable)


    private val animationX = animation.x
    private val animationY = animation.y


    override fun doLogic(delta: Float) {
        (animation as ILogicable).doLogic(delta)
    }

    override fun draw(batcher: Batch, parentOffsetX: Float, parentOffsetY: Float) {
        animation.setLocation(animationX + parentOffsetX, animationY + parentOffsetY)
        (animation as IDrawable).draw(batcher)
    }

    override fun clickAction() {
    }

    override fun unclickAction() {
    }

    override fun intendedUserAction() {
        runnable.run()
    }
}

