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
import org.neubauerfelix.manawars.manawars.handlers.StringUtils

class BoxUnitInfoSimple(x: Float, y: Float, width: Float, val unit: IDataUnit) : MComponentContainer(x, y) {



    init {
        val iconSize = CDConstants.UI_MENU_UNITINFO_ICON_SIZE
        val icon = CDComponentUnit(width / 2f - iconSize / 2f, 0f, iconSize, iconSize, unit, Runnable {  })
        addComponent(icon)

        val table = generateInfoTable(0f, icon.bottom + MConstants.UI_DISTANCE_COLUMNS, width)
        table.centerHorizontal = width / 2f
        addComponent(table)
    }


    // TODO: Translate Keys, add number formatting (cut nachkommastelle wenn nicht vorhanden), etc.

    fun generateInfoTable(x: Float, y: Float, width: Float): IComponent {
        val lang = MManaWars.m.getLanguageHandler()
        val keys = mutableListOf(
                "${lang.getMessage("stats_health")}:"
        )
        val values = mutableListOf(
                unit.health.toInt().toString()
        )

        if (unit.armor != MWArmorType.NONE) {
            keys.add("${lang.getMessage("stats_armor")}:")
            val armor = unit.armor
            val colorAsColorCode = StringUtils.colorAsColorCode(armor.color)
            values.add(colorAsColorCode + lang.getMessage("armor_" +armor.name.toLowerCase()))
        }


        val action = unit.action
        if (action is IDataSkill) {
            keys.add("${lang.getMessage("stats_damage")}:")
            values.add(action.damage.toInt().toString())

            keys.add("${lang.getMessage("stats_skillclass")}:")
            val skillclass = action.skillClass
            val skillclassDisplayName = lang.getMessage("skillclass_${skillclass.name.toLowerCase()}")
            val colorAsColorCode = StringUtils.colorAsColorCode(skillclass.color)
            values.add(colorAsColorCode + skillclassDisplayName)

            val stateEffect = action.stateEffect
            if (stateEffect != null) {
                keys.add("${lang.getMessage("stats_stateeffect")}:")
                val effectDisplayName = lang.getMessage("stateeffect_${stateEffect.name.toLowerCase()}")
                val colorAsColorCode = StringUtils.colorAsColorCode(stateEffect.color)
                values.add("${action.stateEffectDuration}s $colorAsColorCode$effectDisplayName")
            }
        } else if (action is DataSkillMixLoaded) {
            keys.add("${lang.getMessage("stats_damage")}:")
            val damage = action.parts.map { it.action.damage }.sum()
            values.add(damage.toInt().toString())

            keys.add("${lang.getMessage("stats_skillclass")}:")
            val skillclass = action.parts.first().action.skillClass
            val skillclassDisplayName = lang.getMessage("skillclass_${skillclass.name.toLowerCase()}")
            val colorAsColorCode = StringUtils.colorAsColorCode(skillclass.color)
            values.add(colorAsColorCode + skillclassDisplayName)
        }

        keys.add("${lang.getMessage("stats_cooldown")}:")
        values.add(unit.actionCooldown.toString() + "s")

        keys.add("${lang.getMessage("stats_cost")}:")
        values.add(unit.cost.toString() + " " + lang.transform("%currency%"))

        return MManaWars.m.getComponentFactory().createTable(keys, values, x, y, width, fontScale = 0.8f)
    }




}