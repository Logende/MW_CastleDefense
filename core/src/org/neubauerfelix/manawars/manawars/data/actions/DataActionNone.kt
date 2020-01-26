package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.IDataActionProperties
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

class DataActionNone : IDataAction {

    override val soundPath: String? = null


    override val name: String
        get() = "none"
    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("skill_${name}_name")

    override val previewTexturePath: String = "none"
    override val animationEffect: MWAnimationTypeBodyEffect? = null
    override val weaponType: MWWeaponType? = null

    private val actionProperties = object : IDataActionProperties {
        override val strategicValue: Float = 0f
        override val defensiveStrength: Float = 0f
        override val offensiveStrength: Float = 0f
        override val rangeMaxAvg: Float = 300f
        override val rangeMax: MutableMap<MWEntityAnimationType, Int> = hashMapOf()
        override val rangeMin: MutableMap<MWEntityAnimationType, Int> = hashMapOf()

        init {
            MWEntityAnimationType.values().forEach {
                rangeMax[it] = rangeMaxAvg.toInt()
                rangeMin[it] = 0
            }
        }
    }

    override val displayColor: Color
        get() = Color.WHITE

    override fun action(owner: IActionUser): Boolean {
        return true
    }

    override fun canUse(owner: IActionUser): Boolean {
        return false
    }

    override fun getActionProperties(entityAnimationType: MWEntityAnimationType): IDataActionProperties {
        return actionProperties
    }

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}