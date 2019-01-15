package org.neubauerfelix.manawars.manawars.entities.animation


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.Sprite
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.ISized

open class BodyPart(val bodyPartData: IBodyPartData, scale: Float) : GameRectangle(0f, 0f, 0f, 0f) {
    // TODO: make body parts of different people have different sizes due to transparent pixels. That is required because transparent pixels are no longer included in collision check

    var sprite: Sprite = bodyPartData.createSprite()
    var enabled = true


    var relativeX: Float = 0f
        private set
    var relativeY: Float = 0f
        private set

    val mirror: Boolean
        get() = sprite.isFlipX

    init {
        this.relativeX = x
        this.relativeY = y
        sprite.setSize(sprite.width * scale, sprite.height * scale)
        setSize(sprite.width, sprite.height)
        sprite.setOrigin(bodyPartData.rotationOriginX * scale, bodyPartData.rotationOriginY * scale)
        update(bodyPartData.rotation)
    }


    open fun draw(batcher: Batch, sized: ISized, mirror: Boolean, scale: Float) {
        if (!enabled) {
            return
        }
        x = calculateX(sized.x, mirror, scale)
        y = calculateY(sized.y, scale)
        sprite.setPosition(x, y)
        sprite.draw(batcher)
    }

    /**
     * Calculates the actual x coordinate of this body part in the game world.
     * @param sizedX X coordinate of the actual entity.
     * @param scale Entity body scale. 2.00f leads to the double width and height of the sprite.
     * @param scale Sprite scale. 2.00f leads to the double width and height of the sprite.
     * @return actual x coordinate in the game world.
     */
    fun calculateX(sizedX: Float, mirror: Boolean, scale: Float): Float {
        return if (mirror) {
            (sizedX + (bodyPartData.bodyData.bodyWidth - relativeX - sprite.regionWidth) * scale)
        } else {
            (sizedX + relativeX * scale)
        }
    }

    /**
     * Calculates the actual y coordinate of this body part in the game world.
     * @param sizedY Y coordinate of the actual entity.
     * @param scale Entity body scale. 2.00f leads to the double width and height of the sprite.
     * @return actual y coordinate in the game world.
     */
    fun calculateY(sizedY: Float, scale: Float): Float {
        return (sizedY + relativeY * scale)
    }

    /**
     * Updates the position and rotation of this body part.
     * @param x X coordinate of the body part relative to its body. 0 is
     * @param x X coordinate of the body part relative to the body. 0 is the left.
     * @param y Y coordinate of the body part relative to the body. 0 is the top.
     * @param rotation Rotation in degree.
     */
    fun update(x: Float, y: Float, rotation: Float) {
        this.relativeX = x
        this.relativeY = y
        update(rotation)
    }
    /**
     * Updates the rotation of this body part.
     * @param rotation Rotation in degree.
     */
    open fun update(rotation: Float) {
        val mirror = sprite.isFlipX
        sprite.rotation = rotation * if (mirror) -1 else 1
    }

    /**
     * Updates the position of this body part by adding a certain x and y coordinate to the existing coordinates.
     * @param x X offset.
     * @param y Y offset.
     */
    fun addLocation(x: Int, y: Int) {
        this.relativeX += x
        this.relativeY += y
    }

    /**
     * Updates the color of the body part. Can be `null`. In that case the original texture without color filter is drawn (default).
     * @param c New color.
     */
    fun setColor(c: Color) {
        sprite.color = c
    }

    /**
     * Allows mirroring the body part.
     * @param b If `true` the body part is flipped horizontally.
     */
    open fun setMirror(b: Boolean) {
        if (mirror != b) {
            sprite.flip(true, false)
            sprite.setOrigin(sprite.width - sprite.originX, sprite.originY) //mirror rotation centre
        }
    }

    // TODO: update rectangle rotation when sprite is rotated

    /*fun collides(sized: ISized, mirror: Boolean, scale: Float, intersection: ISized): Boolean {
        return if (!enabled) {
            false
        } else intersection.overlaps(calculateX(sized.x, mirror, scale) + bodyPartData.transparentPixelColumnsLeft * scale,
                calculateY(sized.y, scale) + bodyPartData.transparentPixelRowsBottom,
                width - ((bodyPartData.transparentPixelColumnsLeft + bodyPartData.transparentPixelColumnsRight) * scale),
                height - ((bodyPartData.transparentPixelRowsTop + bodyPartData.transparentPixelRowsBottom) * scale))
    }*/


}
