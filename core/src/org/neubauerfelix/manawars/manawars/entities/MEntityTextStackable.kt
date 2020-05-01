package org.neubauerfelix.manawars.manawars.entities

import org.neubauerfelix.manawars.game.entities.ISized

class MEntityTextStackable() : MEntityText() {
    var topic: String = ""
        private set
    var target: ISized? = null
        private set
    var value = 0f
        private set

    private var destroyAction: (MEntityTextStackable) -> Unit = {}

    /**
     * Creates a new game text which is drawn within the playground for a set duration.
     * @param target Target of the text.
     * @param x Text x coordinate.
     * @param y Text y coordinate.
     * @param valueReal Real value not rounded. Used if the value is modified.
     * @param text Text to draw.
     * @param topic Topic of the text.
     * @param displayTime Time in milliseconds until the text is removed.
     */
    fun init(target: ISized?, x: Float, y: Float, valueReal: Float, text: String, topic: String, displayTime: Int,
             destroyAction: (MEntityTextStackable) -> Unit) {
        super.init(x, y, text, displayTime)
        this.target = target
        this.value = valueReal
        this.topic = topic
        this.destroyAction = destroyAction
    }

    override fun destroyed() {
        super.destroyed()
        destroyAction(this)
    }

    override fun reset() {
        super.reset()
        target = null
        destroyAction = {}
    }
}