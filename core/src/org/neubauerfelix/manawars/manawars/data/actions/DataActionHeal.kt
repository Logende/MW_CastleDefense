package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.ILiving
import org.neubauerfelix.manawars.manawars.entities.ITeamable

abstract class DataActionHeal : IDataActionHeal {


    override fun canUse(owner: IActionUser): Boolean {
        return MManaWars.m.screen.getEntities { e ->
            e is ITeamable && e.team == owner.team &&
                    e.getDistanceHor(owner) < healingRange &&
                    e is ILiving
        }.isNotEmpty()
    }

    override fun action(owner: IActionUser): Boolean {
        MManaWars.m.screen.getEntities { e ->
            e is ITeamable && e.team == owner.team &&
                    e.getDistanceHor(owner) < healingRange &&
                    e is ILiving
        }.forEach { e ->
            (e as ILiving).heal(healingPower)
        }
        return true
    }


    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}