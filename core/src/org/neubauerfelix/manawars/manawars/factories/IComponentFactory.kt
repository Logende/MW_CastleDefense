package org.neubauerfelix.manawars.manawars.factories

import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.IHandler
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

interface IComponentFactory : IHandler{


    fun createTable(keys: List<String>, values: List<String>, x: Float, y: Float,
                    width: Float, maximumKeyColumnPercentage: Float = 0.7f, distanceColumns: Float = MConstants.UI_DISTANCE_COLUMNS,
                    distanceRows: Float = MConstants.UI_DISTANCE_ROWS,
                    fontScale: Float): IComponent

    fun createComponentText(text: String, font: FontHandler.MWFont, maximumWidth: Float = Float.MAX_VALUE, distanceLines: Float = MConstants.UI_LINE_DISTANCE,
                            fontScale: Float = 1f): IComponent

}