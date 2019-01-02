package org.neubauerfelix.manawars.game.components

import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.GameRectangle

/**
 * Components use an inverse coordinate system with the origin at the top left corner.
 */
abstract class GameComponent(x: Float, y: Float, width: Float = 0f, height: Float = 0f) :  GameRectangle(x, y, width, height), IComponent {


}