package org.neubauerfelix.manawars.game


interface IScreenTimed: IScreen{

    fun getGameTime(): Long
    fun getTimeSpeedModifier(): Float
    fun setTimeSpeedModifier(f: Float)

}