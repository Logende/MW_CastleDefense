package org.neubauerfelix.manawars.manawars.entities.animation


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.entities.ISized

/**
 * Represents animated human body parts. For example used by bows.
 * @author Felix Neubauer
 */
open class BodyPartAnimation(private val textures: Array<TextureRegion>, bodyPartData: IBodyPartData, scale: Float) : BodyPart(bodyPartData, scale) {

    private var position: Int = 0


    /**
     * Updates the animation image.
     * @param i Index of the image to show.
     * @pre `0 <= i < textures.length`
     */
    fun setPosition(pos: Int) {
        assert(pos in 0 until textures.size)
        this.position = pos
    }

    override fun doLogic(sized: ISized, mirror: Boolean, scale: Float) {
        x = calculateX(sized.x, mirror, scale)
        y = calculateY(sized.y, scale)
    }

    override fun draw(batcher: Batch, sized: ISized, mirror: Boolean, scale: Float) {
        if (!enabled) {
            return
        }
        batcher.color = color
        batcher.draw(textures[position], if (mirror) x+width else x, y,
                if (mirror) originX-width else originX, originY,
                if (mirror) -width else width, height,
                1f, 1f,
                rotation)
        batcher.color = Color.WHITE
    }


}
