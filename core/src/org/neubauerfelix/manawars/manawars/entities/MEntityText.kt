package org.neubauerfelix.manawars.manawars.entities

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.Pool.Poolable
import org.neubauerfelix.manawars.game.IDrawable
import org.neubauerfelix.manawars.game.entities.GameEntity
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

open class MEntityText protected constructor() : GameEntity(0f, 0f), IDrawable, ILogicable, Poolable {
    private var text: String = ""
    private var font: FontHandler.MWFont = FontHandler.MWFont.PLAYGROUND
    private var despawnTime: Long = 0

    protected fun init(x: Float, y: Float, text: String, displayTime: Int) {
        setLocation(x, y)
        this.text = text
        despawnTime = MManaWars.m.screen.getGameTime() + displayTime
        font = FontHandler.MWFont.PLAYGROUND
    }

    override fun doLogic(delta: Float) {
        if (isDespawn) {
            remove = true
            return
        }
    }

    override fun draw(delta: Float, batcher: Batch) {
        font.getFont(1f).draw(batcher, text, x, y)
    }


    private val isDespawn: Boolean
        private get() = MManaWars.m.screen.getGameTime() > despawnTime

    override fun reset() {
        remove = false
    }
}