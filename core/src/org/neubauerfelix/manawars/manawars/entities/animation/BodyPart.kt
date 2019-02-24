package org.neubauerfelix.manawars.manawars.entities.animation


import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.ISized

open class BodyPart(val bodyPartData: IBodyPartData, scale: Float) : GameRectangle(0f, 0f, 0f, 0f) {
    // TODO: make body parts of different people have different sizes due to transparent pixels. That is required because transparent pixels are no longer included in collision check

    var tr = bodyPartData.textureRegion
    var enabled = true

    var color: Color = Color.WHITE

    var relativeX: Float = bodyPartData.relativeX
        private set
    var relativeY: Float = bodyPartData.relativeY
        private set

    open var mirror: Boolean = false
        set(value) {
            if (field != value) {
                super.rotation *= -1
                super.originX = if (mirror) width-super.originX else (super.originX-width)*(-1)
                field = value
            }
        }


    override var rotation: Float
        get() = super.rotation
        set(value) {
            super.rotation = value * (if (mirror) -1 else 1)
        }
    override var originX: Float
        get() = super.originX
        set(value) {
            super.originX = if (mirror) width-value else value
        }


    init {
        setSize(tr.regionWidth * scale, tr.regionHeight * scale)
        setRotationOrigin(bodyPartData.rotationOriginX * scale, bodyPartData.rotationOriginY * scale)
        this.rotation = bodyPartData.rotation
    }


    open fun doLogic(sized: ISized, mirror: Boolean, scale: Float) {
        x = calculateX(sized.x, mirror, scale)
        y = calculateY(sized.y, scale)
    }

    open fun draw(batcher: Batch, sized: ISized, mirror: Boolean, scale: Float) {
        if (!enabled) {
            return
        }
        batcher.color = color
       // System.out.println("draw body part in color " + color)
        batcher.draw(tr, if (mirror) x+width else x, y,
                if (mirror) originX-width else originX, originY,
                if (mirror) -width else width, height,
                1f, 1f,
                rotation)
        batcher.color = Color.WHITE
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
            (sizedX + (bodyPartData.bodyData.bodyWidth - relativeX - tr.regionWidth) * scale)
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
        this.rotation = rotation
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




}
