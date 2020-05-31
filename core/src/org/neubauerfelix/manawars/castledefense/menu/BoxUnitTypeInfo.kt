package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWUnitType
import org.neubauerfelix.manawars.manawars.handlers.FontHandler

class BoxUnitTypeInfo(x: Float, y: Float, width: Float, val unitType: MWUnitType, unitsScale: Float,
                      unitRunnable: (IDataUnit) -> Unit) :
        MComponentContainer(x, y) {


    init {
        val title = MTextLabel(0f, 0f, unitType.displayName, FontHandler.MWFont.HEADING, 2f)
        title.x = width / 2 - title.width / 2
        addComponent(title)

        val tribes = CDManaWars.cd.getTribeHandler().listTribes()
        val units = arrayListOf<IDataUnit>()
        val index = unitType.index
        for (tribe in tribes) {
            val tribeUnits = tribe.army.units
            units.add(tribeUnits[index])
        }

        val unitBoxY = title.bottom + MConstants.UI_DISTANCE_COLUMNS * 1
        val unitsInfoBox = BoxUnitsInfoIcons(0f, unitBoxY, width, units, unitRunnable, unitsScale)
        addComponent(unitsInfoBox)
    }




}