package org.neubauerfelix.manawars.game.entities



open class GameRectangle(x: Float, y: Float, override var width: Float, override var height: Float):
        GameLocation(x, y), ISized {

    constructor(width: Float, height: Float) : this(0f, 0f, width, height)


    override var centerHorizontal: Float
        get() = x + width / 2
        set(f) {x = f - width /2}

    override var centerVertical: Float
        get() = y + height / 2
        set(f) {y = f - height /2}

    var centerLocation: GameLocation
        get() = GameLocation(centerHorizontal, centerVertical)
        set(l) {
            centerHorizontal = l.x
            centerVertical = l.y
        }

    override var right: Float
        get() = x + width
        set(f) {x = f - width}

    override var bottom: Float
        get() = y
        set(f) {y = f}

    override var left: Float
        get() = x
        set(f) {x = f}

    override var top: Float
        get() = y + height
        set(f) {y = f - height}



    fun pasteRectangle(e: ISized, onlyTemporaryValues: Boolean) {
        super.pasteLocated(e, onlyTemporaryValues)
        if(!onlyTemporaryValues) {
            width = e.width
            height = e.height
        }
    }

    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }


    override fun getDistanceHor(x: Float): Float {
        return if (x < this.x) {
            Math.abs(this.x - x)
        } else if (x > this.right) {
            Math.abs(x - this.right)
        } else {
            0f
        }
    }

    override fun getDistanceVer(y: Float): Float {
        return if (y < this.y) {
            Math.abs(this.y - y)
        } else if (y > this.bottom) {
            Math.abs(y - this.bottom)
        } else {
            0f
        }
    }

    override fun getDistance(s: ISized): Double {
        return Math.sqrt(Math.pow(getDistanceHor(s), 2.0) + Math.pow(getDistanceVer(s).toDouble(), 2.0))
    }

    override fun getDistanceHor(s: ISized): Double {
        return if (s.right < this.left) {
            Math.abs(s.right - this.left).toDouble()
        } else if (s.left > this.right) {
            Math.abs(s.left - this.right).toDouble()
        } else {
            0.0
        }
    }

    override fun getDistanceVer(s: ISized): Float {
        return if (s.bottom < this.top) {
            Math.abs(s.bottom - this.top)
        } else if (s.top > this.bottom) {
            Math.abs(s.top - this.bottom)
        } else {
            0f
        }
    }


    override fun getIntersection(r2: ISized, bounds: GameRectangle) {
        getIntersection(r2.x, r2.y, r2.width, r2.height, bounds)
    }

    override fun getIntersection(rx: Float, ry: Float, rw: Float, rh: Float, bounds: GameRectangle) {
        val left = Math.max(x, rx)
        val bottom = Math.max(y, ry)
        val right = Math.min(x + width, rx + rw)
        val top = Math.min(y + height, ry + rh)
        bounds.x = left
        bounds.y = top
        bounds.width = right - left
        bounds.height = bottom - top
    }

    override fun overlaps(r2: ISized): Boolean { //unable to handle zoomed objects atm
        return overlaps(r2.x, r2.y, r2.width, r2.height)
    }

    override fun overlaps(rx: Float, ry: Float, rw: Float, rh: Float): Boolean {
        return !(y + height < ry || y > ry + rh || x + width < rx || x > rx + rw)
    }

    override fun isInside(px: Float, py: Float): Boolean {
        return (x <= px && px <= x + width && y <= py && py <= y + height)
    }


}
