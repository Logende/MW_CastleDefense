package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Colors
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.IDataActionProperties
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponClass
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import org.neubauerfelix.manawars.manawars.storage.Configuration
import java.util.*

class DataActionHealLoaded(override val name: String, config: Configuration) : DataActionHeal() {

    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("skill_${name}_name")

    override val healingPower: Float = config.getFloat("healingPower")
    override val healingRange: Float = config.getFloat("healingRange")
    override val displayColor: Color =
            if (config.contains("color")) {
                Colors.get(config.getString("color"))
            } else {
                Color.WHITE
            }

    override val previewTexturePath = config.getString("preview")
    override val animationEffect: MWAnimationTypeBodyEffect? = if (config.contains("owner_animation"))
    { MWAnimationTypeBodyEffect.valueOf(config.getString("owner_animation")) } else { null }

    override val weaponType: MWWeaponType?
    init {
        if (config.contains("weapon") && animationEffect == MWAnimationTypeBodyEffect.WEAPON) {
            val weaponParts = config.getString("weapon").split(":")
            val weaponClass = MWWeaponClass.valueOf(weaponParts[0].toUpperCase())
            val textureName = weaponParts[1].replace("_", ".")
            weaponType = MWWeaponType(weaponClass, textureName)
        } else {
            weaponType = null
        }
    }
    override val soundPath: String? = config.getString("sound")


    val actionProperties = object : IDataActionProperties {
        override val strategicValue: Float = healingPower * 3 // because multiple units can be healed
        override val defensiveStrength: Float = strategicValue
        override val offensiveStrength: Float = 0f
        override val rangeMax: Map<MWEntityAnimationType, Int>
        override val rangeMin: Map<MWEntityAnimationType, Int>


        init {
            rangeMax = EnumMap(MWEntityAnimationType::class.java)
            rangeMin = EnumMap(MWEntityAnimationType::class.java)
            MWEntityAnimationType.values().forEach {
                rangeMax[it] = Int.MAX_VALUE
                rangeMin[it] = 0
            }
        }

        override val rangeMaxAvg: Float = Int.MAX_VALUE.toFloat()
    }

    override fun getActionProperties(entityAnimationType: MWEntityAnimationType): IDataActionProperties {
        return actionProperties
    }
}