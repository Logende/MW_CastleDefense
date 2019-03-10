package org.neubauerfelix.manawars.castledefense.components

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


    private val frameTop: TextureRegion
    private val frameBottom: TextureRegion // TODO: more frames for rider?
    private val background: TextureRegion
    private val animation: IEntity

    init {
        frameBottom = MManaWars.m.getImageHandler().getTextureRegionButton("frame.red.bottom")
        frameTop = MManaWars.m.getImageHandler().getTextureRegionButton("frame.red.top")
        background = MManaWars.m.getImageHandler().getTextureRegionButton("credits")
        animation = unit.animation.produce(x + width*0.05f, y + height*0.05f, width*0.9f, height*0.9f)
    }

    override fun draw(delta: Float, batcher: Batch, offsetX: Float, offsetY: Float) {
        batcher.draw(background, x, y, width, height)
        batcher.draw(frameTop, x, y, width, height/2f)
        batcher.draw(frameBottom, x, y + height/2, width, height/2f)
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

