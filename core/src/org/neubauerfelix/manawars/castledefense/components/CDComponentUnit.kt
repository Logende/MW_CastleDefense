package org.neubauerfelix.manawars.castledefense.components

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit

class CDComponentUnit(x: Float, y: Float, width: Float, height: Float, val unit : IDataUnit, listener: Runnable) :
        CDComponentCoreEntity(x, y, width, height, unit, listener, this.getBackground(unit), unit.armor.color) {

    companion object {
        private fun getBackground(unit: IDataUnit) : TextureRegion {
            return if (unit.drainMultiplier == 0f) {
                MManaWars.m.getImageHandler().getTextureRegionButton("frame.background")
            } else {
                MManaWars.m.getImageHandler().getTextureRegionButton("frame.background.vampire")
            }
        }
    }

}

