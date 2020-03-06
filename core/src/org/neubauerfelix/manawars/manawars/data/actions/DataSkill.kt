package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.MSkill


abstract class DataSkill : IDataSkill {

    override val displayColor: Color
        get() = skillClass.color


    override fun canUse(owner: IActionUser): Boolean {
        if (targetEnemy) {
            if (MManaWars.m.getSkillSetupHandler().findTarget(this, owner) == null) {
                return false
            }
        }
        if (skillLimit > 0) {
            if (MManaWars.m.screen.getEntities { e ->
                        e is MSkill && e.owner == owner && e.data == this }
                            .size >= skillLimit) {
                return false
            }
        }
        return true
    }

    override fun action(owner: IActionUser): Boolean {
        this.spawnSkill(owner)
        return true
    }

    override fun spawnSkill(owner: IActionUser): IEntity {
        val s = MSkill(this, owner)
        s.spawn()
        return s
    }

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(x: Float, y: Float, width: Float, height: Float, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
