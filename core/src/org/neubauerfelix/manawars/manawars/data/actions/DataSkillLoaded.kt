package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Colors
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWSkillClass
import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataSkillLoaded(config: Configuration) : DataSkill() {


    /**
     * Appearance: Animation, sound, etc.
     */
    override val texturePath: String = config.getString("texture")
    override val previewTexturePath: String = if (config.contains("preview")) { config.getString("preview") } else { texturePath }

    override val animationFrequency: Float
    override val textureColumns: Int
    override val textureRows: Int
    init {
        val animation = config.getString("animation").split(":")
        animationFrequency = animation[1].toFloat()
        textureColumns = animation[2].toInt()
        textureRows = animation[3].toInt()
    }

    override val pickOneFrame: Boolean = config.getBoolean("pick_one_frame")
    override val animationRotationDuration: Float = config.getFloat("rotation_duration", 2f)
    override val color: Color? =
            if (config.contains("color")) {
                Colors.get(config.getString("color"))
            } else {
                null
            }

    override val soundPath: String = config.getString("sound")
    override val textureWidth: Int
    override val textureHeight: Int
    override val textureScale: Float = config.getFloat("scale")

    override val animationEffect: MWAnimationTypeBodyEffect? = if (config.contains("owner_animation")) { MWAnimationTypeBodyEffect.valueOf(config.getString("owner_animation")) } else { null }
    override val weaponType: MWWeaponType? = if (config.contains("weapon")) { null } else { null } // TODO!!


    /**
     * Effect: Knockback, damage, state effect, etc.
     */
    override val skillStrength: Int = config.getInt("strength")
    override val damageMin: Int
    override val damageMax: Int
    init {
        val damage = config.getString("damage").split("-")
        damageMin = damage[0].toInt()
        damageMax = if (damage.size >=2) { damage[1].toInt() } else { damage[0].toInt() }
    }
    override val stateEffect: MWState?
    override val stateEffectDuration: Float
    init {
        if (config.contains("effect")) {
            val effect = config.getString("effect").split(":")
            stateEffect = MWState.valueOf(effect[0])
            stateEffectDuration = effect[1].toFloat()
        } else {
            stateEffect = null
            stateEffectDuration = 0f
        }
    }
    override val knockbackFactor: Float = config.getFloat("knockback", 1f)
    override val spawnOnImpact: String? = if (config.contains("spawn_on_impact")) { config.getString("spawn_on_impact") } else { null }


    /**
     * Movement: speed, location offset, targets, etc.
     */
    override val idleTime: Float = config.getFloat("idle_time") // in seconds
    override val yRelativeToGround: Boolean = config.getBoolean("y_relative_to_ground")
    override val xOffset: Float
    override val yOffset: Float
    override val startSpeedX: Float
    override val startSpeedY: Float
    override val accelerationX: Float
    override val accelerationY: Float
    init {
        val offset = config.getString("location").split(":")
        xOffset = offset[0].toFloat()
        yOffset = offset[1].toFloat()

        val speed = config.getString("speed").split(":")
        startSpeedX = speed[0].toFloat()
        startSpeedY = speed[1].toFloat()

        val acceleration = config.getString("acceleration").split(":")
        accelerationX = acceleration[0].toFloat()
        accelerationY = acceleration[1].toFloat()
    }

    override val stopOnGround: Boolean = config.getBoolean("stop_on_ground")
    override val adaptiveSpeedX: Boolean = config.getBoolean("adaptive_speed_x")
    override val fixManaCost: Int = config.getInt("manacost", -1)
    override val targetEnemy: Boolean = config.contains("target_range") || config.contains("target_speed") || config.contains("adaptive_speed_x")
    override val targetRange: Float = config.getFloat("target_range", 900f)
    override val xRelativeToTarget: Boolean
    override val yRelativeToTarget: Boolean
    override val targetSpeedX: Float
    override val targetSpeedY: Float
    init {
        if (config.contains("spawn_relative_to_target")) {
            val spawnRelativeToTarget = config.getString("spawn_relative_to_target").split(":")
            xRelativeToTarget = spawnRelativeToTarget[0].toBoolean()
            yRelativeToTarget = spawnRelativeToTarget[1].toBoolean()
        } else {
            xRelativeToTarget = false
            yRelativeToTarget = false
        }
        if (config.contains("target_speed")) {
            val targetSpeed = config.getString("target_speed").split(":")
            targetSpeedX = targetSpeed[0].toFloat()
            targetSpeedY = targetSpeed[1].toFloat()
        } else {
            targetSpeedX = 0f
            targetSpeedY = 0f
        }
    }


    /**
     * Classification: Skillclass, range and other properties
     */
    override val skillClass: MWSkillClass = MWSkillClass.valueOf(config.getString("skillclass"))
    override val name: String
    override val actionDependencies: Array<IDataAction> // TODO


}