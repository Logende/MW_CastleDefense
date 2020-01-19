package org.neubauerfelix.manawars.castledefense.entities

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.MEntityAnimated

open class CDEntityBuilding(data: IDataBuilding) : MEntityAnimated(data.animationProducer,
        data.health), ILiving


