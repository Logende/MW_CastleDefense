package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.manawars.enums.MWState
import org.neubauerfelix.manawars.manawars.storage.Configuration

class DataSkillEffectLoaded(config: Configuration) : IDataSkillEffect {


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
        if (!config.getString("effect").isEmpty()) {
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

}