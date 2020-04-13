package org.neubauerfelix.manawars.manawars.data

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer

interface IDataCoreEntity {

    val animation: IEntityAnimationProducer
    val action: IDataAction
    val cost: Int
}