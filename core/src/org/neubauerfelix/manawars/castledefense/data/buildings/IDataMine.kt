package org.neubauerfelix.manawars.castledefense.data.buildings

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.entities.ICDEntityCastle
import org.neubauerfelix.manawars.game.entities.IEntity


interface IDataMine {

    val chargePeriod: Float
    val goldPerCharge: Int
    val animation: Animation<TextureRegion>

    fun produce(centreHor: Float, bottom: Float, castle: ICDEntityCastle): IEntity
}

