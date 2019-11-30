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
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class CDComponentUnit(x: Float, y: Float, width: Float, height: Float, val unit : IDataUnit, val listener: UnitClickListener) :
        MComponent(x, y, width, height) {


    private val background: TextureRegion
    private val text: IComponent


    private val animation: IEntity

    init {
        background = MManaWars.m.getImageHandler().getTextureRegionButton("frame.background")
        // TODO: Gold armor for boss?
        animation = unit.animation.produce(x + width * 0.1f, y + height*0.05f, width*0.8f, height*0.8f)

        val color = unit.action.displayColor
        val colorAsHexString = String.format("#%02x%02x%02x",
                (color.r * 255f).toInt(),
                (color.g * 255f).toInt(),
                (color.b * 255f).toInt())
        text = MTextLabel(x, y, "[$colorAsHexString]${unit.cost}", FontHandler.MWFont.MAIN, 0.2f)
    }

    override fun draw(delta: Float, batcher: Batch, offsetX: Float, offsetY: Float) {
        batcher.draw(background, x, y, width, height)
        MManaWars.m.getCharacterBarHandler().drawArmorFrame(batcher, x, y, width, height, unit.animation.animationType,
                unit.armor)
        batcher.color = Color.WHITE
        (animation as IDrawable).draw(delta, batcher)
        (animation as ILogicable).doLogic(delta)

        val offsetX = (width - text.width) * 0.5f
        val offsetY = (height - text.height) * 0.8f
        text.draw(delta, batcher, offsetX, offsetY)
    }

    override fun clickAction() {
        listener.clickedUnit(unit)
    }

    override fun unclickAction() {
    }


    interface UnitClickListener {
        fun clickedUnit(unit: IDataUnit)
    }
}

