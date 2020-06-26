package org.neubauerfelix.manawars.castledefense.data.buildings

import com.badlogic.gdx.graphics.g2d.Animation
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataMineLoaded(config: Configuration) :
        DataMine(
                config.getFloat("chargePeriod"),
                config.getInt("goldPerChargeInitial"),
                config.getInt("goldIncreasePerCharge"),
                config.getInt("goldPerChargeMax"),
                Animation(0f, MManaWars.m.getImageHandler().getTextureRegionMain("building.mine"))
                ) {


}