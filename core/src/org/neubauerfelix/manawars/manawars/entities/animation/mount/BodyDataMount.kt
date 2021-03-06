package org.neubauerfelix.manawars.manawars.entities.animation.mount


import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartData
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartData


/**
 * This class allows preparing skin textures for human skeletons.
 * It loads the different body parts and is capable of reducing the size of the head texture rectangle by cutting of transparent edges
 * in order to make accurate collision handling possible.
 * @author Felix Neubauer
 */
class BodyDataMount(override val skinName: String, textureRegion: TextureRegion, override val defaultScale: Float = 1f):
        IBodyDataMount {


    override val head: IBodyPartData
    override val body: IBodyPartData
    override val footBL: IBodyPartData
    override val footBR: IBodyPartData
    override val footFL: IBodyPartData
    override val footFR: IBodyPartData


    init {
        val tx = textureRegion.regionX
        val ty = textureRegion.regionY - textureRegion.regionHeight
        val texture = textureRegion.texture
        val trHead = TextureRegion(texture, 76 + tx, 0 + ty, 100, 87)
        val trBody = TextureRegion(texture, 0 + tx, 87 + ty, MConstants.SKIN_MOUNT_WIDTH, 71)

        val trFootBL = TextureRegion(texture, 0 + tx, 5 + ty, 38, 41)
        val trFootBR = TextureRegion(texture, 38 + tx, 5 + ty, 38, 41)
        val trFootFL = TextureRegion(texture, 0 + tx, 46 + ty, 38, 41)
        val trFootFR = TextureRegion(texture, 38 + tx, 46 + ty, 38, 41)

        trHead.flip(false, true)
        trBody.flip(false, true)
        trFootBL.flip(false, true)
        trFootBR.flip(false, true)
        trFootFL.flip(false, true)
        trFootFR.flip(false, true)

        //TODO: transparent pixels: pre-load from storage file
        head = BodyPartData(trHead, 112f, 0f, 0f, 0f, 0f, this,
                0, 0, 0, 0)
        body = BodyPartData(trBody, 0f, 42f, 0f, 0f, 0f, this,
                0, 0, 0, 0)
        footBL = BodyPartData(trFootBL, 82f, 94f, 11f, 9f, 0f, this,
                0, 0, 0, 0)
        footBR = BodyPartData(trFootBR, 145f, 94f, 11f, 9f, 0f, this,
                0, 0, 0, 0)
        footFL = BodyPartData(trFootFL, 52f, 94f, 11f, 9f, 0f, this,
                0, 0, 0, 0)
        footFR = BodyPartData(trFootFR, 115f, 94f, 11f, 9f, 0f, this,
                0, 0, 0, 0)
    }




}
