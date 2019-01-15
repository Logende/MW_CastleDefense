package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.IComponent
import org.neubauerfelix.manawars.game.data.GameData
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.entities.skills.Skill
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass
import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType


class DataSkillExample : DataSkill() {

    override val texturePath: String
        get() = "skills/magic.fireball.png"
    override val textureColumns: Int
        get() = 2
    override val textureRows: Int
        get() = 1
    override val pickOneFrame: Boolean
        get() = false
    override val animationFrequency: Float
        get() = 2f
    override val animationRotationDuration: Float
        get() = 3f
    override val color: Color
        get() = Color.BLUE
    override val soundPath: String
        get() = ""
    override val textureWidth: Int
        get() = 200
    override val textureHeight: Int
        get() = 100
    override val textureScale: Float
        get() = 1f
    override val skillClass: MWSkillClass
        get() = MWSkillClass.MAGIC
    override val skillStrength: Int
        get() = 10
    override val damageMin: Int
        get() = 1
    override val damageMax: Int
        get() = 10
    override val stateEffect: MWState
        get() = MWState.BURNING
    override val stateEffectDuration: Float
        get() = 3f
    override val knockbackFactor: Float
        get() = 1f
    override val spawnOnImpact: String?
        get() = null
    override val idleTime: Float
        get() = 0f
    override val lifeTime: Float
        get() = 10f
    override val yRelativeToGround: Boolean
        get() = false
    override val xOffset: Float
        get() = 0f
    override val yOffset: Float
        get() = 140f
    override val startSpeedX: Float
        get() = 400f
    override val startSpeedY: Float
        get() = 0f
    override val accelerationX: Float
        get() = 0f
    override val accelerationY: Float
        get() = 0f
    override val stopOnGround: Boolean
        get() = false
    override val adaptiveSpeedX: Boolean
        get() = false
    override val fixManaCost: Int
        get() = -1
    override val targetEnemy: Boolean
        get() = false
    override val targetRange: Float
        get() = 0f
    override val xRelativeToTarget: Boolean
        get() = false
    override val yRelativeToTarget: Boolean
        get() = false
    override val targetSpeedX: Float
        get() = 0f
    override val targetSpeedY: Float
        get() =0f

    override fun spawnSkill(owner: IActionUser): IEntity {
        val s = Skill(this, owner)
        s.spawn()
        return s
    }

    override val name: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val previewTexturePath: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val manaCost: Int
        get() = 100
    override val animationEffect: MWAnimationTypeBodyEffect?
        get() = MWAnimationTypeBodyEffect.THROW
    override val weaponType: MWWeaponType?
        get() = null
    override val actionDependencies: Array<IDataAction>
        get() = arrayOf()
    override val rangeMax: Int
        get() = 1000
    override val rangeMin: Int
        get() = 0


    override fun canUse(owner: IEntity): Boolean {
        return true
    }

    override fun generateInfo(x: Int, y: Int, width: Int, height: Int): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun generateIcon(size: Int, action: Runnable): IComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
