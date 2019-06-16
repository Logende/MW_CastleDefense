package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.data.GameData
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.MSkill


abstract class DataSkill : GameData(), IDataSkill {


    override fun init() {
        // TODO add sound
        this.addAsset(this.model)
        this.actionDependencies.forEach { dependency -> this.addAsset(dependency) }
    }

    override fun load() {
    }

    override fun loaded() {
    }

    override fun dispose() {
    }

    override fun disposed() {
    }



    override fun canUse(owner: IActionUser): Boolean {
        if (model.targetEnemy) {
            if (MManaWars.m.getSkillSetupHandler().findTarget(this, owner) == null) {
                return false
            }
        }
        if (model.skillLimit > 0) {
            if (MManaWars.m.screen.getEntities { e ->
                        e is MSkill && e.owner == owner && e.data == this }
                            .size >= model.skillLimit) {
                return false
            }
        }
        return true
    }

    override fun action(owner: IActionUser): Boolean {
        return this.spawnSkill(owner) != null
    }

    override fun spawnSkill(owner: IActionUser): IEntity {
        val s = MSkill(this, effect, owner)
        s.spawn()
        return s
    }

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
