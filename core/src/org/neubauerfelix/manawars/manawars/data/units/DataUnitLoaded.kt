package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.castledefense.data.tribes.IDataArmy
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillLoaded
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import java.util.*

class DataUnitLoaded(override val name: String, config: Configuration, val army: IDataArmy,
                     override val unitType: MWUnitType) : DataUnit() {

    override val unitRarity: MWUnitRarity = MWUnitRarity.valueOf(config.getString("rarity").toUpperCase())
    private val baseUnitStats = MManaWars.m.getBaseUnitHandler().getBaseUnitStats(unitType, unitRarity)


    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("unit_${name}_name")
    override val cost: Int = if (config.contains("cost")) {
        (baseUnitStats.cost * config.getFloat("cost")).toInt()
    } else {
        baseUnitStats.cost
    }


    override val animation: IEntityAnimationProducer = IEntityAnimationProducer
            .createProducer(config.getString("animation"))

    override val armor: MWArmorType = if (!config.contains("armor")) {
        MWArmorType.NONE
    } else {
        MWArmorType.valueOf(config.getString("armor").toUpperCase())
    }


    override val action: IDataAction = MManaWars.m.getActionHandler().loadAction("skill_$name", config.getSection("action"))!!

    init {
        if (action is DataSkillLoaded) {
            if (action.damage <= 0) {
                action.damage = baseUnitStats.damage.toInt()
            } else {
                action.damage = (action.damage * baseUnitStats.damage).toInt()
            }
        }
    }


    override var health: Float = if (config.contains("health")) {
        (baseUnitStats.health * config.getFloat("health"))
    } else {
        baseUnitStats.health
    }

    override var actionCooldown: Float  = if (config.contains("cooldown")) {
        (baseUnitStats.cooldown * config.getFloat("cooldown"))
    } else {
        baseUnitStats.cooldown
    }
    override val stateMultipliers: MutableMap<MWState, MWStateEffectivity> = EnumMap(MWState::class.java)
    override val skillMultipliers: MutableMap<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java)
    override val skillDurabilityMultipliers: MutableMap<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java)
    override var drainMultiplier: Float = config.getFloat("drain")

    override var walkSpeedMax: Float = if (config.contains("walkSpeedMax")) {
        config.getFloat("walkSpeedMax", 1f) * MConstants.UNIT_AVG_WALK_SPEED_MAX
    } else {
        if (animation.animationType == MWEntityAnimationType.RIDER) {
            MConstants.UNIT_AVG_WALK_SPEED_MAX * 2f
        } else {
            MConstants.UNIT_AVG_WALK_SPEED_MAX * 1f
        }
    }

    override var walkAcceleration: Float = if (config.contains("walkAcceleration")) {
        config.getFloat("walkAcceleration", 1f) * MConstants.UNIT_AVG_WALK_ACC
    } else {
        if (animation.animationType == MWEntityAnimationType.RIDER) {
            MConstants.UNIT_AVG_WALK_ACC * 2f
        } else {
            MConstants.UNIT_AVG_WALK_ACC * 1f
        }
    }

}
