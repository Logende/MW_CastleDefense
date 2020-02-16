package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponClass
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import org.neubauerfelix.manawars.manawars.storage.Configuration
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DataSkillMixLoaded(override val name: String, config: Configuration) : IDataAction {

    override val soundPath: String? = null

    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("skill_${name}_name")

    override val previewTexturePath: String = config.getString("preview")

    override val displayColor: Color

    val parts: List<DataSkillMixPart>

    init {
        parts = ArrayList()
        val actionDependencies = arrayListOf<IDataAction>()

        for (line in config.getStringList("parts")) {
            val parts = line.split(":")
            val skillname = parts[0]
            val offsetX = if (parts.size >= 2) parts[1] else "0"
            val offsetY = if (parts.size >= 3) parts[2] else "0"
            val offsetSpeedX = if (parts.size >= 4) parts[3] else "0"
            val offsetSpeedY = if (parts.size >= 5) parts[4] else "0"
            val offsetIdleTime = if (parts.size >= 6) parts[5] else "0"
            val action = MManaWars.m.getActionHandler().loadAction(skillname,
                    config.getSection("recipes").getSection(skillname))

            if (!actionDependencies.contains(action)) {
                actionDependencies.add(action)
            }

            this.parts.add(DataSkillMixPart(action as IDataSkill, offsetX, offsetY, offsetSpeedX, offsetSpeedY,
                    offsetIdleTime))
        }
        this.displayColor = parts.first().action.displayColor
    }


    override val animationEffect: MWAnimationTypeBodyEffect? = if (config.contains("owner_animation")) {
        MWAnimationTypeBodyEffect.valueOf(config.getString("owner_animation"))
    } else { null }

    override val weaponType: MWWeaponType?
    init {
        if (config.contains("weapon") && animationEffect == MWAnimationTypeBodyEffect.WEAPON) {
            val weaponParts = config.getString("weapon").split(":")
            val weaponClass = MWWeaponClass.valueOf(weaponParts[0].toUpperCase(Locale.getDefault()))
            val textureName = weaponParts[1].replace("_", ".")
            weaponType = MWWeaponType(weaponClass, textureName)
        } else {
            weaponType = null
        }
    }


    override fun action(owner: IActionUser): Boolean {
        return parts.map { part -> part.spawnSkill(owner) }.contains(true)
    }

    override fun canUse(owner: IActionUser): Boolean {
        return parts.map { part -> part.action.canUse(owner) }.contains(true)
    }

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override val rangeMax: Float =  parts.map { it.action.rangeMax }.min()!!
    override val rangeMin: Float =  parts.map { it.action.rangeMin }.min()!!


}