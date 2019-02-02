package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass
import org.neubauerfelix.manawars.manawars.enums.MWState


/**
 * Basic interface of skill data; used by every skill. Acts like a "building plan" for skills. One building plan provides the
 * ability to spawn an unlimited amounts of skills of that type.
 * Contains every method needed to work with skills while the individual implementations execute all requires background tasks.
 * @author Felix Neubauer
 */
interface IDataSkill : IDataAction {

    // animation data
    val texturePath: String
    val textureColumns: Int
    val textureRows: Int
    val pickOneFrame: Boolean // If enabled instead of showing the whole skill animation one frame is randomly chosen and drawn per skill
    val animationFrequency: Float
    val animation: Animation<TextureRegion>?
    val animationRotationDuration: Float
    val color: Color?

    val soundPath: String

    // texture size
    val textureWidth: Int
    val textureHeight: Int
    val textureScale: Float // affects just skill size, not other properties like damage or strength


    // categories
    val skillClass: MWSkillClass

    // attack action
    val skillStrength: Int
    val damageMin: Int
    val damageMax: Int
    val stateEffect: MWState?
    val stateEffectDuration: Float
    val knockbackFactor: Float
    val spawnOnImpact: String? // spawns certain action on impact with enemy

    // movement
    val idleTime: Float // Time in s the skill idles before it moves and damages enemies
    val lifeTime: Float // Life time of skill in s (does not include idle time)
    val yRelativeToGround: Boolean
    val xOffset: Float
    val yOffset: Float
    val startSpeedX: Float
    val startSpeedY: Float
    val accelerationX: Float
    val accelerationY: Float
    val stopOnGround: Boolean // most skills just fall through ground but some can stop and stay there (shield)

    /**
     * If enabled this will scale speed and acceleration to make the skill perfectly hit the (static) enemy
     * This will result in following skill position when skill and target collide:
     * - Skill centre is at target centre
     * - Skill has the same y coordinate as when it was spawned
     * Requires targetEnemy=true
     */
    val allowMovementScaling: Boolean

    // special properties
    val fixManaCost: Int // use -1 to disable fix mana cost
    val skillLimit: Int

    // target properties
    val targetEnemy: Boolean // if true skill can only be used if target is there
    val targetRange: Float
    val xRelativeToTarget: Boolean
    val yRelativeToTarget: Boolean
    val targetSpeedX: Float // overrides normal speed if >0. This is the speed the skill will follow targets with
    val targetSpeedY: Float // overrides normal speed if >0. This is the speed the skill will follow targets with


    /**
     * Executes the skill action which is for example spawning one skill or a set of other skills.
     * @param owner Skill owner.
     * @return returns the created skill. If multiple skills were spawned only one of them is returned. In case of failure null is returned.
     */
    fun spawnSkill(owner: IActionUser): IEntity


}
