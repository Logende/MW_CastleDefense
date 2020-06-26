package org.neubauerfelix.manawars.manawars.data

import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.NWRarity

interface IDataCoreEntity {

    val animation: IEntityAnimationProducer
    val action: IDataAction
    val cost: Int
    val rarity: NWRarity
}