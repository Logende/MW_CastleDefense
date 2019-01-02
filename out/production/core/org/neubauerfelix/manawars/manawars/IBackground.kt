package org.neubauerfelix.manawars.manawars

import org.neubauerfelix.manawars.game.IDisposable
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.ILoadableAsync

interface IBackground : IDrawable, IDisposable, ILoadableAsync {
}