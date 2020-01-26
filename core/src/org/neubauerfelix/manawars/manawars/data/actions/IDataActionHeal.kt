package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass


interface IDataSkillModel : IAsset, IDataActionLook {

    // animation data
    val texturePath: String
    val textureColumns: Int
    val textureRows: Int
    val pickOneFrame: Boolean // If enabled instead of showing the whole skill animation one frame is randomly chosen and drawn per skill
    val animationFrequency: Float
    val animation: Animation<TextureRegion>?
    val animationRotationDuration: Float
    val color: Color?
    val textureScale: Float // affects just skill size, not other properties like damage or strength

    val healingPower: Float // health to heal
    val healingRange: Float



    // categories
    val skillClass: MWSkillClass



    // movement
    val idleTime: Float // Time in s the skill idles before it moves and damages enemies
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
     * - MSkill centre is at target centre
     * - MSkill has the same y coordinate as when it was spawned
     * Requires targetEnemy=true
     */
    val allowMovementScaling: Boolean

    val skillLimit: Int
    val loaded: Boolean

    // target properties
    val targetEnemy: Boolean // if true skill can only be used if target is there
    val targetRange: Float
    val xRelativeToTarget: Boolean
    val yRelativeToTarget: Boolean
    val targetSpeedX: Float // overrides normal speed if >0. This is the speed the skill will follow targets with
    val targetSpeedY: Float // overrides normal speed if >0. This is the speed the skill will follow targets with




}
