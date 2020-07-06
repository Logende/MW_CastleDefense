package org.neubauerfelix.manawars.manawars.entities.animation.human

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.animation.BodyPartDataShield
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyData
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartData
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartDataScalable
import org.neubauerfelix.manawars.manawars.enums.MWShield

interface IBodyDataHuman: IBodyData {

    val skinName: String
    val head: IBodyPartData
    val body: IBodyPartData
    val footL: IBodyPartData
    val footR: IBodyPartData
    val armL: IBodyPartData
    val armR: IBodyPartData
    val shieldType: MWShield?
    val shield: IBodyPartDataScalable?


    override val bodyHeight: Int
        get() = MConstants.BODY_HUMAN_HEIGHT

    override val bodyWidth: Int
        get() = MConstants.BODY_HUMAN_WIDTH

}