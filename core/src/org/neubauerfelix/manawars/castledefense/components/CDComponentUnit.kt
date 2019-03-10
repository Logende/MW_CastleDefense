package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.MComponent
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class CDComponentUnit(x: Float, y: Float, width: Float, height: Float, unit : IDataUnit) :
        MComponent(x, y, width, height) {


    private val background: TextureRegion
    private val frame: Map<TextureRegion, Color>


    private val animation: IEntity

    init {
        background = MManaWars.m.getImageHandler().getTextureRegionButton("frame.background")
        frame = hashMapOf()
        unit.armor.forEach { entry ->
            val texturePath = "frame.unit." +
                    "${unit.animation.animationType.name.toLowerCase().replace("_", "")}." +
                    "${entry.key.name.toLowerCase().replace("_", "")}"

             val texture = MManaWars.m.getImageHandler().getTextureRegionButton(texturePath)
            frame[texture] = entry.value.color
        }
        // TODO: Gold armor for boss?
        animation = unit.animation.produce(x + width * 0.1f, y + height*0.05f, width*0.8f, height*0.8f)
    }

    override fun draw(delta: Float, batcher: Batch, offsetX: Float, offsetY: Float) {
        batcher.draw(background, x, y, width, height)
        for ((texture, color) in this.frame) {
            batcher.color = color
            batcher.draw(texture, x, y, width, height)
        }
        batcher.color = Color.WHITE
        (animation as IDrawable).draw(delta, batcher)
        (animation as ILogicable).doLogic(delta)
    }

    override fun clickAction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun unclickAction() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

