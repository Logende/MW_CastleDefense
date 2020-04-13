package org.neubauerfelix.manawars.castledefense.components

import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.manawars.MManaWars

class CDComponentBuilding(x: Float, y: Float, width: Float, height: Float, val building: IDataBuilding,
                          listener: Runnable) :
        CDComponentCoreEntity(x, y, width, height, building, listener,
                MManaWars.m.getImageHandler().getTextureRegionButton("frame.background"),
                null) {


}

