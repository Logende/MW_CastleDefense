package org.neubauerfelix.manawars.manawars.entities.animation

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion

class BodyPartData(val textureRegion: TextureRegion, override val relativeX: Float, override val relativeY: Float,
                   override val rotationOriginX: Float, override val rotationOriginY: Float, override val rotation: Float,
                   override val bodyData: IBodyData, override val transparentPixelRowsTop: Int,
                   override val transparentPixelRowsBottom: Int, override val transparentPixelColumnsLeft: Int,
                   override val transparentPixelColumnsRight: Int): IBodyPartData{


    override fun createSprite(): Sprite {
        return Sprite(textureRegion)
    }

}