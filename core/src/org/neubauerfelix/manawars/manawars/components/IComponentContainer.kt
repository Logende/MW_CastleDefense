package org.neubauerfelix.manawars.manawars.components

import org.neubauerfelix.manawars.game.IComponent

interface IComponentContainer : IComponent {

    fun addComponent(component: IComponent, above: Boolean = true)
    fun removeComponent(component: IComponent)
    fun clearComponents()
}