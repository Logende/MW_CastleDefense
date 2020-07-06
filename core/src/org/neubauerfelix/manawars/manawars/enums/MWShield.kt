package org.neubauerfelix.manawars.manawars.enums

import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartDataShield
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyData
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartData
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartDataScalable


class MWShield(val textureName: String, val isEnchanted: Boolean) {


    fun createSprite(): Sprite {
        return Sprite(textureRegion)
    }

    private val textureRegion: TextureRegion
        get() = AManaWars.m.getImageHandler().getTextureRegionMain("char.shield.$textureName")

    fun createBodyPartData(bodyData: IBodyData): IBodyPartDataScalable {
        return BodyPartDataShield(textureRegion, bodyData, MConstants.SHIELD_SCALE)
    }
}


