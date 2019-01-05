package org.neubauerfelix.manawars.manawars.entities.animation.mount

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyData
import org.neubauerfelix.manawars.manawars.entities.animation.IBodyPartData

interface IBodyDataMount: IBodyData {

    val skinName: String
    val head: IBodyPartData
    val body: IBodyPartData
    val footFL: IBodyPartData
    val footFR: IBodyPartData
    val footBL: IBodyPartData
    val footBR: IBodyPartData

    override val bodyHeight: Int
        get() = MConstants.BODY_MOUNT_HEIGHT

    override val bodyWidth: Int
        get() = MConstants.BODY_MOUNT_WIDTH

}