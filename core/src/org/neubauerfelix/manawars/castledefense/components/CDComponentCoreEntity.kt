package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.MTextLabel
import org.neubauerfelix.manawars.manawars.data.IDataCoreEntity
import org.neubauerfelix.manawars.manawars.handlers.FontHandler
import org.neubauerfelix.manawars.manawars.handlers.StringUtils

open class CDComponentCoreEntity(x: Float, y: Float, width: Float, height: Float, val entity: IDataCoreEntity,
                                runnable: Runnable, val background: TextureRegion,
                                 private val frameColor : Color?) :
        CDComponentEntity(x, y, width, height, entity.animation.produce(x + width * 0.1f, y + height*0.05f,
                width*0.8f, height*0.8f, entity.action.weaponType), runnable), ILogicable {


    private val text: IComponent


    init {
        val color = entity.action.displayColor
        val colorAsColorCode = StringUtils.colorAsColorCode(color)
        text = MTextLabel(x, y, "$colorAsColorCode${entity.cost}", FontHandler.MWFont.MAIN, 0.2f)
    }

    override fun doLogic(delta: Float) {
        (animation as ILogicable).doLogic(delta)
    }

    override fun draw(batcher: Batch, parentOffsetX: Float, parentOffsetY: Float) {
        batcher.draw(background, parentOffsetX + x, parentOffsetY + y, width, height)

        super.draw(batcher, parentOffsetX, parentOffsetY)

        MManaWars.m.getCharacterBarHandler().drawFrame(batcher, parentOffsetX + x, parentOffsetY + y,
                width, height, frameColor)

        val offsetX = (width - text.width) * 0.5f
        val offsetY = (height - text.height) * 0.8f
        text.draw(batcher, parentOffsetX + offsetX, parentOffsetY + offsetY)
    }

}

