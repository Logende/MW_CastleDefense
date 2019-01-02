package org.neubauerfelix.manawars.game.entities

interface ISized : ILocated {


    var width: Float
    var height: Float
    var centerHorizontal: Float
    var centerVertical: Float
    var right: Float
    var bottom: Float
    var left: Float
    var top: Float

    /**
     * Checks if two rectangles are intersecting.
     *
     * @param r2 Rectangle to check intersection with.
     * @return `true` if they do intersect.
     */
    fun overlaps(r2: ISized): Boolean

    /**
     * Checks if two rectangles are intersecting. The given coordinates represent the second rectangle.
     *
     * @param rx x coordinate of the second rectangle.
     * @param ry y coordinate of the second rectangle.
     * @param rw width of the second rectangle.
     * @param rh height of the second rectangle.
     * @return `true` if they do intersect.
     */
    fun overlaps(rx: Float, ry: Float, rw: Float, rh: Float): Boolean

    /**
     * Calculates which pixels of this rectangle and `r2` overlap and writes the collision bounds into the `bounds` rectangle.
     * Requires an intersection of both rectangles in order to work properly.
     *
     * @param r2     The collision bounds of `this` and `r2` are returned.
     * @param bounds collision bounds are stored here.
     * @return bounds of the overlapping pixels.
     */
    fun getIntersection(r2: ISized, bounds: GameRectangle)

    /**
     * Calculates which pixels of this rectangle and the given one overlap and writes the collision bounds into the `bounds` rectangle.
     * Requires an intersection of both rectangles in order to work properly.
     *
     * @param rx     x coordinate of the second rectangle.
     * @param ry     y coordinate of the second rectangle.
     * @param rw     width of the second rectangle.
     * @param rh     height of the second rectangle.
     * @param bounds collision bounds are stored here.
     * @return bounds of the overlapping pixels.
     */
    fun getIntersection(rx: Float, ry: Float, rw: Float, rh: Float, bounds: GameRectangle)

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
     * Checks if the given coordinates are inside the rectangle.
     *
     * @param px X coordinate.
     * @param py Y coordinate.
     * @return `true` if the coordinates are inside the rectangle.
     */
    fun isInside(px: Float, py: Float): Boolean

}
