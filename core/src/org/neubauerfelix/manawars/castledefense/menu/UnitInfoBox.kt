package org.neubauerfelix.manawars.castledefense.menu

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.castledefense.components.CDComponentUnit
import org.neubauerfelix.manawars.game.GameConstants
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.components.*
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillMixLoaded
import org.neubauerfelix.manawars.manawars.data.actions.IDataSkill
import org.neubauerfelix.manawars.manawars.data.units.IDataUnit
import org.neubauerfelix.manawars.manawars.entities.MEntityImage
import org.neubauerfelix.manawars.manawars.entities.controller.ControllerAction
import org.neubauerfelix.manawars.manawars.enums.MWArmorType

class UnitInfoBox(x: Float, y: Float, val previewBackground: TextureRegion, val unit: IDataUnit) : MComponentContainer(x, y) {


    val background: IEntity
    val entity: IEntity
    val dummy: IEntity

    init {

        val iconSize = 200f
        val icon = CDComponentUnit(GameConstants.SCREEN_WIDTH / 2f - iconSize / 2f, MConstants.MENU_TITLE_Y,
                iconSize, iconSize, unit, Runnable {  })
        addComponent(icon)

        val boxBorder = 100f
        val boxHeight = previewBackground.regionHeight.toFloat()
        val boxWidth = previewBackground.regionWidth.toFloat()
        val boxY = GameConstants.WORLD_HEIGHT_UNITS - boxHeight
        background = MEntityImage(boxBorder, boxY, boxWidth, boxHeight, previewBackground)
        background.spawn()

        val window = MManaWars.m.getCamera().window
        val previousWindowY = window.y
        window.centerVertical = GameConstants.WORLD_HEIGHT_UNITS - 240f
        val offsetY = previousWindowY - window.y

        entity = unit.produce(background.left + 20f, GameConstants.WORLD_HEIGHT_UNITS - unit.animation.bodyHeight, ControllerAction(),
                MConstants.TEAM_PLAYER)
        entity.speedY = 0f
        entity.accelerationY = 0f
        dummy = unit.produce(background.left + 490f, GameConstants.WORLD_HEIGHT_UNITS - unit.animation.bodyHeight, ControllerAction(),
                MConstants.TEAM_BOT)
        dummy.speedY = 0f
        dummy.accelerationY = 0f

        val tableX = GameConstants.SCREEN_WIDTH - boxBorder - boxWidth
        val table = generateInfoTable(tableX, background.y + offsetY, boxWidth, boxHeight)
        addComponent(table)
    }


    fun generatePreview(x: Float, y: Float, width: Float, height: Float) {

    }


    fun generateInfoTable(x: Float, y: Float, width: Float, height: Float): IComponent {
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

        return MManaWars.m.getComponentFactory().createTable(keys, values, x, y, width, height)
    }




}