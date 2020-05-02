package org.neubauerfelix.manawars.game

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.Screen
import org.neubauerfelix.manawars.game.entities.IEntity

interface IScreen: IDisposable, ILoadable, Screen {

    fun getGame(): AManaWars
    fun getState(): ScreenState
    fun getInputProcessor(): InputProcessor

    fun requestNotificationWhenAssetsLoaded()

    fun getEntities(condition: (IEntity) -> Boolean): List<IEntity>
    fun containsEntity(entity: IEntity): Boolean
    fun addEntity(entity: IEntity)

    fun addComponent(component: IComponent)
    fun removeComponent(component: IComponent)
    fun getComponents() : Iterable<IComponent>

    fun addBackground(background: IDrawable, backgroundLimit: Int = 3)
    fun removeBackground(background: IDrawable)




}