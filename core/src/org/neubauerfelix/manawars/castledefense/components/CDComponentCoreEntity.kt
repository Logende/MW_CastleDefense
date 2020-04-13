package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.MComponent
import org.neubauerfelix.manawars.manawars.components.MTextLabel
import org.neubauerfelix.manawars.manawars.data.IDataCoreEntity
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

open class CDComponentCoreEntity(x: Float, y: Float, width: Float, height: Float, val entity: IDataCoreEntity,
                            private val listener: Runnable, val background: TextureRegion,
                            private val frameColor : Color?) :
        MComponent(x, y, width, height) {


    private val text: IComponent


    private val animation: IEntity = entity.animation.produce(x + width * 0.1f, y + height*0.05f,
            width*0.8f, height*0.8f)
    // the real animation x and y values are stored because they can differ from the given ones (because x/y
    // relation is kept 1:1 and the given allowedWidth and allowedHeight might leave open space)
    private val animationX = animation.x
    private val animationY = animation.y

    init {
        val color = entity.action.displayColor
        val colorAsHexString = String.format("#%02x%02x%02x",
                (color.r * 255f).toInt(),
                (color.g * 255f).toInt(),
                (color.b * 255f).toInt())
        text = MTextLabel(x, y, "[$colorAsHexString]${entity.cost}", FontHandler.MWFont.MAIN, 0.2f)
    }

    override fun draw(delta: Float, batcher: Batch, parentOffsetX: Float, parentOffsetY: Float) {
        batcher.draw(background, parentOffsetX + x, parentOffsetY + y, width, height)

        animation.setLocation(animationX + parentOffsetX, animationY + parentOffsetY)
        (animation as IDrawable).draw(delta, batcher)
        (animation as ILogicable).doLogic(delta)

        MManaWars.m.getCharacterBarHandler().drawFrame(batcher, parentOffsetX + x, parentOffsetY + y,
                width, height, frameColor)

        val offsetX = (width - text.width) * 0.5f
        val offsetY = (height - text.height) * 0.8f
        text.draw(delta, batcher, parentOffsetX + offsetX, parentOffsetY + offsetY)
    }

    override fun clickAction() {
        listener.run()
    }

    override fun unclickAction() {
    }

}

