package org.neubauerfelix.manawars.manawars.entities.animation


import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import org.neubauerfelix.manawars.game.entities.ISized

/**
 * Represents animated human body parts. For example used by bows.
 * @author Felix Neubauer
 */
open class BodyPartAnimation(private val sprites: Array<Sprite>, bodyPartData: IBodyPartData, scale: Float) : BodyPart(bodyPartData, scale) {

    private var position: Int = 0


    init {
        for (i in 0 until sprites.size) {
            sprites[i].setSize(sprites[i].regionWidth * scale, sprites[i].regionHeight * scale)
        }
        this.sprite = sprites[0]
    }


    /**
     * Updates the animation image.
     * @param i Index of the image to show.
     * @pre `0 <= i < sprites.length`
     */
    fun setPosition(pos: Int) {
        assert(pos in 0 until sprites.size)
        this.position = pos
    }


    override fun draw(batcher: Batch, sized: ISized, mirror: Boolean, scale: Float) {
        if (!enabled) {
            return
        }
        sprites[position].setPosition(calculateX(sized.x, mirror, scale), calculateY(sized.y, scale))
        sprites[position].draw(batcher)
    }


    override fun update(rotation: Float) {
        for (sprite in sprites) {
            sprite.rotation = rotation
        }
    }

    override fun setMirror(b: Boolean) {
        for (sprite in sprites) {
            if (sprite.isFlipX != b) {
                sprite.flip(true, false)
            }
        }
    }

}
