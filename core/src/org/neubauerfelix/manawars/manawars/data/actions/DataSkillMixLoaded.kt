package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import org.neubauerfelix.manawars.manawars.storage.Configuration



class DataSkillMixLoaded(override val name: String, config: Configuration) : IDataAction {

    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("skill_${name}_name")

    override val previewTexturePath: String = config.getString("preview")


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
    }

    override val manaCost: Int = (this.parts.map { part -> part.action.manaCost }.sum()
            * config.getFloat("manacost_factor", 0.7f)).toInt()

    override val animationEffect: MWAnimationTypeBodyEffect? = if (config.contains("owner_animation")) { MWAnimationTypeBodyEffect.valueOf(config.getString("owner_animation")) } else { null }
    override val weaponType: MWWeaponType? = if (config.contains("weapon")) { null } else { null } // TODO!!



    override val rangeMax: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val rangeMin: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

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
}