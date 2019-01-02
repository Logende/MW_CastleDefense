package org.neubauerfelix.manawars.manawars.entities.animation

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion

class BodyPartDataShield(val textureRegion: TextureRegion, override val bodyData: IBodyData, val scale: Float): IBodyPartData {


    override fun createSprite(): Sprite {
        val sprite = Sprite(textureRegion)
        sprite.setSize(sprite.width * scale, sprite.height * scale)
        return sprite
    }

    override val relativeX: Float
        get() = bodyData.bodyWidth + 14f - textureRegion.regionWidth * scale

    override val relativeY: Float
        get() = bodyData.bodyHeight - textureRegion.regionHeight * scale

    override val rotationOriginX: Float
        get() = textureRegion.regionWidth * scale / 2

    override val rotationOriginY: Float
        get() = 0f

    override val rotation: Float
        get() = 0f

    override val transparentPixelRowsTop: Int
        get() = 0

    override val transparentPixelRowsBottom: Int
        get() = 0
    override val transparentPixelColumnsLeft: Int
        get() = 0
    override val transparentPixelColumnsRight: Int
        get() = 0
}