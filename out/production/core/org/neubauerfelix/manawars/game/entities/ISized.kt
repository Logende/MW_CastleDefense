package org.neubauerfelix.manawars.game.entities

import com.badlogic.gdx.math.Polygon

interface ISized {

    val polygon: Polygon
    var x: Float
    var y: Float
    var width: Float
    var height: Float
    var centerHorizontal: Float
    var centerVertical: Float
    var right: Float
    var bottom: Float
    var left: Float
    var top: Float
    var rotation: Float
    var originX: Float
    var originY: Float


    /**
     * Overwrite the location of the object
     * @param x new x coordinate.
     * Set the y coordinate of the object.
     * @param y new y coordinate.
     */
    fun setLocation(x: Float, y: Float)


    /**
     * Overwrite the location of the object
     * @param l New location.
     */
    fun setLocation(l: ILocated)

    /**
     * Checks if two rectangles are intersecting. Includes rotation of rectangles.
     *
     * @param r2 Rectangle to check intersection with.
     * @return `true` if they do intersect.
     */
    fun overlaps(r2: ISized): Boolean


    /**
     * Get the distance between this rectangle and an other sized object.
     * This will calculate the real distance instead of the distance between the locations of the objects.
     *
     * @param s object to compare with.
     * @return real distance.
     */
    fun getDistance(s: ISized): Double

    /**
     * Returns the real horizontal distance between this rectangle and an other sized object.
     *
     * @param s object to compare with.
     * @return real distance.
     */
    fun getDistanceHor(s: ISized): Double

    /**
     * Returns the real vertical distance between this rectangle and an other sized object.
     *
     * @param s object to compare with.
     * @return real distance.
     */
    fun getDistanceVer(s: ISized): Float


    /**
     * Returns the horizontal distance between the x coordinate of this object and the given coordinate.
     * @param x coordinate to compare with.
     * @return distance between both coordinates.
     */
    fun getDistanceHor(x: Float): Float

    /**
     * Returns the vertical distance between the y coordinate of this object and the given coordinate.
     * @param y coordinate to compare with.
     * @return distance between both coordinates.
     */
    fun getDistanceVer(y: Float): Float


    /**
     * Checks if the given coordinates are inside the rectangle.
     *
     * @param px X coordinate.
     * @param py Y coordinate.
     * @return `true` if the coordinates are inside the rectangle.
     */
    fun isInside(px: Float, py: Float): Boolean

}
