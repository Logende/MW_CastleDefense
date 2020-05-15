package org.neubauerfelix.manawars.castledefense.data.buildings

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.entities.CDEntityMine
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.game.entities.IEntity

class DataMine(override val chargePeriod: Float, override val goldPerChargeInitial: Int,
               override val goldIncreasePerCharge: Int, override val goldPerChargeMax: Int,
               override val animation: Animation<TextureRegion>) :
        IDataMine {




    override fun produce(centreHor: Float, bottom: Float, castle: ICDEntityCastle): IEntity {
        val e =  CDEntityMine(centreHor - animation.getKeyFrame(0f).regionWidth/2f,
                bottom - animation.getKeyFrame(0f).regionHeight,
                animation, chargePeriod, goldPerChargeInitial, goldIncreasePerCharge, goldPerChargeMax,
                castle)
        e.spawn()
        return e
    }

}