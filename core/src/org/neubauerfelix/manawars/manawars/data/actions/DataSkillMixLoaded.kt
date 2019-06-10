package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.analysis.IDataActionProperties
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType
import org.neubauerfelix.manawars.manawars.enums.MWWeaponClass
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import org.neubauerfelix.manawars.manawars.storage.Configuration



class DataSkillMixLoaded(override val name: String, config: Configuration) : IDataAction {

    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("skill_${name}_name")

    override val previewTexturePath: String = config.getString("preview")

    override val displayColor: Color

    private val parts: List<DataSkillMixPart>
    override val actionDependencies: Array<IDataAction>

    init {
        parts = ArrayList()
        val actionDependencies = arrayListOf<IDataAction>()

        for (line in config.getStringList("parts")) {
            val parts = line.split(":")
            val skillname = parts[0]
            var offsetX = if (parts.size >= 2) parts[1] else "0"
            var offsetY = if (parts.size >= 3) parts[2] else "0"
            var offsetSpeedX = if (parts.size >= 4) parts[3] else "0"
            var offsetSpeedY = if (parts.size >= 5) parts[4] else "0"
            val action = MManaWars.m.getActionHandler().getAction(skillname) as IDataSkill

            if (!actionDependencies.contains(action)) {
                actionDependencies.add(action)
            }

            this.parts.add(DataSkillMixPart(action, offsetX, offsetY, offsetSpeedX, offsetSpeedY))
        }
        this.actionDependencies = actionDependencies.toTypedArray()
        this.displayColor = parts.first().action.displayColor
    }


    override val animationEffect: MWAnimationTypeBodyEffect? = if (config.contains("owner_animation")) { MWAnimationTypeBodyEffect.valueOf(config.getString("owner_animation")) } else { null }
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

    override fun loadAsset() {
        parts.forEach { part -> part.action.loadAsset() }
    }

    override fun loadedAsset() {
        parts.forEach { part -> part.action.loadedAsset() }
    }

    override fun disposeAsset() {
        parts.forEach { part -> part.action.disposeAsset() }
    }


    val properties: Map<MWEntityAnimationType, IDataActionProperties>

    init {
        properties = HashMap()


        MWEntityAnimationType.values().forEach { type ->

            val rangeMaxAvg = parts.map { it.action.getActionProperties(type).rangeMaxAvg }.min()!!
            val strategicValue = parts.map { it.action.getActionProperties(type).strategicValue }.average()
            val successProbability = parts.map { it.action.getActionProperties(type).successProbability }.sum() * 0.8f
            val defensiveStrength = parts.map { it.action.getActionProperties(type).defensiveStrength }.sum()
            val offensiveStrength = parts.map { it.action.getActionProperties(type).offensiveStrength }.sum()

            properties[type] = object : IDataActionProperties {

                override val rangeMaxAvg: Float = rangeMaxAvg
                override val strategicValue: Float = strategicValue.toFloat()
                override val successProbability: Float = successProbability
                override val defensiveStrength: Float = defensiveStrength
                override val offensiveStrength: Float = offensiveStrength
                override val rangeMax: Map<MWEntityAnimationType, Int> = parts.last().action.getActionProperties(type).rangeMax
                override val rangeMin: Map<MWEntityAnimationType, Int> = parts.last().action.getActionProperties(type).rangeMin // TODO
            }
        }
    }

    override fun getActionProperties(entityAnimationType: MWEntityAnimationType): IDataActionProperties {
        return properties.getValue(entityAnimationType)
    }


}