package org.neubauerfelix.manawars.manawars.entities.animation

import com.badlogic.gdx.graphics.g2d.Sprite

interface IBodyPartData {

    fun createSprite(): Sprite
    val relativeX: Float
    val relativeY: Float
    val rotationOriginX: Float
    val rotationOriginY: Float
    val rotation: Float
    val bodyData: IBodyData
    val transparentPixelRowsTop: Int
    val transparentPixelRowsBottom: Int
    val transparentPixelColumnsLeft: Int
    val transparentPixelColumnsRight: Int



}