package org.neubauerfelix.manawars.castledefense.components

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class CDComponentScheduledUnit(x: Float, y: Float, width: Float, height: Float, val unit : IDataUnit, listener: Runnable) :
        CDComponentCoreEntity(x, y, width, height, unit, listener,
                MManaWars.m.getImageHandler().getTextureRegionButton("frame.scheduled"), unit.armor.color,
                false)
