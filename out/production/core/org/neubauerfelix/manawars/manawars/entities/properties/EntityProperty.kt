package org.neubauerfelix.manawars.manawars.entities.properties

import org.neubauerfelix.manawars.manawars.entities.IComposed

abstract class EntityProperty: IEntityProperty {

    override lateinit var entity: IComposed

}