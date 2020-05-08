package org.neubauerfelix.manawars.castledefense.menu

import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillMixLoaded
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.enums.MWArmorType

class UnitInfoBoxSimple(x: Float, y: Float, width: Float, val unit: IDataUnit) : MComponentContainer(x, y) {



    init {
        val iconSize = CDConstants.UI_MENU_UNITINFO_ICON_SIZE
        val icon = CDComponentUnit(width / 2f - iconSize / 2f, 0f, iconSize, iconSize, unit, Runnable {  })
        addComponent(icon)

        val table = generateInfoTable(0f, icon.bottom + MConstants.UI_DISTANCE_COLUMNS, width)
        table.centerHorizontal = width / 2f
        addComponent(table)
    }



    fun generateInfoTable(x: Float, y: Float, width: Float): IComponent {
        val keys = mutableListOf(
                "Health:"
        )
        val values = mutableListOf(
                unit.health.toString()
        )

        // TODO text formatting and translation

        val action = unit.action
        if (action is IDataSkill) {
            keys.add("Damage:")
            values.add(action.damage.toString())

            val stateEffect = action.stateEffect
            if (stateEffect != null) {
                keys.add("Effect:")
                values.add(stateEffect.name)
            }
        } else if (action is DataSkillMixLoaded) {
            keys.add("Damage:")
            val damage = action.parts.map { it.action.damage }.sum()
            values.add(damage.toString())
        }

        keys.add("Cooldown:")
        values.add(unit.actionCooldown.toString() + "s")

        if (unit.armor != MWArmorType.NONE) {
            keys.add("Armor:")
            values.add(unit.armor.name)
        }

        keys.add("Cost:")
        values.add(unit.cost.toString())

        return MManaWars.m.getComponentFactory().createTable(keys, values, x, y, width)
    }




}