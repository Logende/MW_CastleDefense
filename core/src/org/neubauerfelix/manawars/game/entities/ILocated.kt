package org.neubauerfelix.manawars.game.entities

/**
 * Contains basic methods which every object with a location needs to be capable of.
 * @author Felix Neubauer
 */
interface ILocated {

    /**
     * Get the x coordinate of the object.
     * In case of a sized object this represents the left of the object.
     * @return x coordinate.
     */
    /**
     * Set the x coordinate of the object.
     * @param x new x coordinate.
     */
    var x: Float

    /**
     * Get the y coordinate of the object.
     * In case of a sized object this represents the top of the object.
     * @return y coordinate.
     */
    /**
     * Set the y coordinate of the object.
     * @param y new y coordinate.
     */
    var y: Float

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
     * Returns the distance between this location and the given one.
     * @param loc location to compare with.
     * @return distance between both locations.
     */
    fun getDistance(loc: ILocated): Float

    /**
     * Returns the distance between this location and the given coordinates.
     * @param x X coordinate.
     * @param y Y coordinate.
     * @return distance between both locations.
     */
    fun getDistance(x: Float, y: Float): Float

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
     * Checks if the given location is in the range.
     * @param loc Location.
     * @param range Range.
     * @return `true` if the given location is within the defined range of this location.
     */
    fun isInRange(loc: ILocated, range: Double): Boolean


}
