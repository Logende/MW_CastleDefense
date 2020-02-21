package org.neubauerfelix.manawars.castledefense.data.actions

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.castledefense.data.buildings.IDataBuilding
import org.neubauerfelix.manawars.castledefense.entities.CDEntityBuildingPlaceholder
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.ITeamable
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType

class DataActionBuild(override val name: String, override val building: IDataBuilding) : IDataActionBuild {

    // basically everything is ready, except the unit which uses the build action.
    // probably auto-create buttons for all buildings and have units with building skills connected to those buttons


    override fun canUse(owner: IActionUser): Boolean {
        return getAvailableBuildingPlaceholders(owner).isNotEmpty()
    }

    override fun action(owner: IActionUser): Boolean {
        val entities = getAvailableBuildingPlaceholders(owner)
        return if (entities.isNotEmpty()) {
            val placeholder = entities.first() as CDEntityBuildingPlaceholder
            placeholder.build(this.building, owner.team)
            owner.remove = true
            true
        } else {
            false
        }
    }

    fun getAvailableBuildingPlaceholders(owner: IActionUser) : List<IEntity> {
        return MManaWars.m.screen.getEntities { e ->
            e is ITeamable && e.team == owner.team &&
                    e.getDistanceHor(owner) == 0f &&
                    e is CDEntityBuildingPlaceholder
        }
    }


    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(x: Float, y: Float, width: Float, height: Float, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override val displayColor: Color = Color.WHITE
    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("skill_${name}_name")
    override val previewTexturePath: String = ""
    override val animationEffect: MWAnimationTypeBodyEffect? = null
    override val weaponType: MWWeaponType? = null
    override val soundPath: String?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.


    override val rangeMax: Float = 0f
    override val rangeMin: Float = 0f
}