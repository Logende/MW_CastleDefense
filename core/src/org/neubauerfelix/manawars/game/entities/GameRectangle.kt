package org.neubauerfelix.manawars.game.entities

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Polygon


open class GameRectangle(x: Float, y: Float, width: Float, height: Float) : ISized {

    constructor(width: Float, height: Float) : this(0f, 0f, width, height)

    override var x: Float
        get() = polygon.x
        set(value) {
            polygon.setPosition(value, y)
        }

    override var y: Float
        get() = polygon.y
        set(value) {
            polygon.setPosition(x, value)
        }

    override var width: Float = width
        @Deprecated("Resizing rectangles requires creating a new Polygon instance every time")
        set(value) {
            val x = this.x
            val y = this.y
            polygon = Polygon(floatArrayOf(0f, 0f, value, 0f, value, height, 0f, height))
            polygon.setPosition(x, y)
            field = value
        }

    override var height: Float = height
        @Deprecated("Resizing rectangles requires creating a new Polygon instance every time")
        set(value) {
            val x = this.x
            val y = this.y
            polygon = Polygon(floatArrayOf(0f, 0f, width, 0f, width, value, 0f, value))
            polygon.setPosition(x, y)
            field = value
        }

    final override var polygon: Polygon
        private set

    init {
        polygon = Polygon(floatArrayOf(0f, 0f, width, 0f, width, height, 0f, height))
        polygon.setOrigin(width /2, height / 2)
        polygon.setPosition(x, y)
    }


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
        get() = y + height
        set(f) {y = f - height}

    override var left: Float
        get() = x
        set(f) {x = f}

    override var top: Float
        get() = y
        set(f) {y = f}


    fun setRotationOrigin(originX: Float, originY: Float) {
        polygon.setOrigin(originX, originY)
    }

    override var originX: Float
        get() = polygon.originX
        set(value) { polygon.setOrigin(value, polygon.originY) }

    override var originY: Float
        get() = polygon.originY
        set(value) { polygon.setOrigin(polygon.originX, value) }

    override var rotation: Float
        get() = polygon.rotation
        set(value) {
            polygon.rotation = value
        }



    fun pasteRectangle(e: ISized, onlyTemporaryValues: Boolean) {
        x = e.x
        y = e.y
        rotation = e.rotation
        if(!onlyTemporaryValues) {
            width = e.width
            height = e.height
            originX = e.originX
            originY = e.originY
        }
    }

    @Deprecated("Resizing rectangles requires creating a new Polygon instance every time")
    fun setSize(width: Float, height: Float) {
        this.width = width
        this.height = height
    }

    override fun setLocation(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    override fun setLocation(l: ILocated) {
        this.setLocation(l.x, l.y)
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

    override fun getDistance(s: ISized): Float {
        return Math.sqrt(Math.pow(getDistanceHor(s).toDouble(), 2.0) + Math.pow(getDistanceVer(s).toDouble(), 2.0)).toFloat()
    }

    override fun getDistanceHor(s: ISized): Float {
        return if (s.right < this.left) {
            Math.abs(s.right - this.left)
        } else if (s.left > this.right) {
            Math.abs(s.left - this.right)
        } else {
            0f
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



    override fun isInside(px: Float, py: Float): Boolean {
        val vertices = polygon.transformedVertices
        return Intersector.isPointInPolygon(vertices, 0, vertices.size, px, py)
    }



}
