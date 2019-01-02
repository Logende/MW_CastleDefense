package org.neubauerfelix.manawars.manawars.entities.animation.human

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartData
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartData
import org.neubauerfelix.manawars.manawars.enums.MWShield

class BodyDataHuman(override val skinName: String, textureRegion: TextureRegion, override val shieldType: MWShield?, override val defaultScale: Float = 1f):
        IBodyDataHuman {


    override val head: IBodyPartData
    override val body: IBodyPartData
    override val footL: IBodyPartData
    override val footR: IBodyPartData
    override val armL: IBodyPartData
    override val armR: IBodyPartData
    override val shield: IBodyPartData?


    init {
        val tx = textureRegion.regionX
        val ty = textureRegion.regionY
        val texture = textureRegion.texture
        val trHead = TextureRegion(texture, 0 + tx, 0 + ty, MConstants.SKIN_HUMAN_WIDTH, 85)
        val trBody = TextureRegion(texture, 0 + tx, 85 + ty, MConstants.SKIN_HUMAN_WIDTH, 65)

        val trFootL = TextureRegion(texture, 0 + tx, 150 + ty, 20, 12)
        val trFootR = TextureRegion(texture, 0 + tx, 162 + ty, 20, 12)

        val trArmL = TextureRegion(texture, 20 + tx, 150 + ty, 30, 30)
        val trArmR = TextureRegion(texture, 50 + tx, 150 + ty, 30, 30)

        val height = MConstants.BODY_HUMAN_HEIGHT

        //TODO: transparent pixels: pre-load from storage file
        head = BodyPartData(trHead, 0f, height - 11f - trHead.regionHeight, 0f, 0f, 0f, this,
                0, 0, 0, 0)
        body = BodyPartData(trBody, 0f, height - 85f - trBody.regionHeight, 0f, 0f, 0f, this,
                0, 0, 0, 0)
        armL = BodyPartData(trArmL, 20f, height -100f - trArmL.regionHeight, 9f, trArmL.regionHeight - 6f, 0f, this,
                0, 0, 0, 0)
        armR = BodyPartData(trArmR, 65f, height -100f - trArmR.regionHeight, 9f, trArmR.regionHeight - 6f, 0f, this,
                0, 0, 0, 0)
        footL = BodyPartData(trFootL, 30f, height -150f - trFootL.regionHeight, 8f, trFootL.regionWidth.toFloat(), 0f, this,
                0, 0, 0, 0)
        footR = BodyPartData(trFootR, 60f, height -150f - trFootR.regionHeight, 8f, trFootR.regionWidth.toFloat(), 0f, this,
                0, 0, 0, 0)
        shield = when{
            shieldType != null -> shieldType.createBodyPartData(this)
            else -> null
        }
    }


}