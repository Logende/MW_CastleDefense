package org.neubauerfelix.manawars.manawars.entities.properties

import com.badlogic.gdx.graphics.g2d.Batch
import org.neubauerfelix.manawars.manawars.entities.IComposed

interface IEntityProperty {

    var entity: IComposed

    fun init(entity: IComposed){
        this.entity = entity
    }


    fun doLogic(delta: Float)
    fun drawAbove(batcher: Batch, delta: Float)
    fun paste(property: IEntityProperty)

}
