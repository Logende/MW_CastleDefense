package org.neubauerfelix.manawars.game.entities


open class GameLocation(x: Float, y: Float) : ILocated {

    override var x: Float = x
    override var y: Float = y


    fun pasteLocated(e: ILocated, onlyTemporaryValues: Boolean) {
        x = e.x
        y = e.y
    }


    override fun setLocation(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    override fun setLocation(l: ILocated) {
        setLocation(l.x, l.y)
    }

    override fun getDistance(loc: ILocated): Float {
        return getDistance(loc.x, loc.y)
    }

    override fun getDistance(x: Float, y: Float): Float {
        return Math.sqrt(Math.pow(getDistanceHor(x).toDouble(), 2.0) + Math.pow(getDistanceVer(y).toDouble(), 2.0)).toFloat()
    }

    override fun getDistanceHor(x: Float): Float {
        return Math.abs(x - this.x)
    }

    override fun getDistanceVer(y: Float): Float {
        return Math.abs(y - this.y)
    }

    override fun isInRange(loc: ILocated, range: Double): Boolean {
        return getDistance(loc) <= range
    }

    fun plus(l: ILocated): GameLocation {
        this.x += l.x
        this.y += l.y
        return this
    }
    fun minus(l: ILocated): GameLocation {
        this.x -= l.x
        this.y -= l.y
        return this
    }


}