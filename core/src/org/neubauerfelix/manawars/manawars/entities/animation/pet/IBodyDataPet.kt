package org.neubauerfelix.manawars.manawars.entities.animation.pet

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyData

interface IBodyDataPet: IBodyData {

    val skinName: String
    val textures: Array<TextureRegion>
    val frameDuration: Float

}