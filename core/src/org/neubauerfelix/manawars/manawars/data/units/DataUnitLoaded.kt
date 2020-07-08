package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.castledefense.data.tribes.IDataArmy
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillLoaded
import org.neubauerfelix.manawars.manawars.data.actions.DataSkillMixLoaded
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.*
import org.neubauerfelix.manawars.manawars.storage.Configuration
import java.util.*

class DataUnitLoaded(override val name: String, config: Configuration, val army: IDataArmy,
                     override val unitType: MWUnitType) : DataUnit() {

    override val rarity: NWRarity = NWRarity.COMMON // NWRarity.valueOf(config.getString("rarity").toUpperCase())
    private val baseUnitStats = MManaWars.m.getBaseUnitHandler().getBaseUnitStats(unitType, rarity)


    override val displayName: String

    init {
        val languageHandler = MManaWars.m.getLanguageHandler()
        displayName = if (languageHandler.hasMessage("unit_${name}_name")) {
            languageHandler.getMessage("unit_${name}_name")
        } else {
            val tribeNameSingular = languageHandler.getMessage("tribe_${army.tribe.name.toLowerCase()}_name_singular")
            tribeNameSingular + " " + unitType.displayName
        }
    }

    override val cost: Int = if (config.contains("cost")) {
        (baseUnitStats.cost * config.getFloat("cost")).toInt()
    } else {
        baseUnitStats.cost
    }


    override val animation: IEntityAnimationProducer = IEntityAnimationProducer
            .createProducer(config.getString("animation"))

    override val armor: MWArmorType = if (!config.contains("armor") ||! MConstants.USE_ARMOR_MECHANIC) {
        MWArmorType.NONE
    } else {
        MWArmorType.valueOf(config.getString("armor").toUpperCase())
    }


    override val action: IDataAction = MManaWars.m.getActionHandler().loadAction("skill_$name", config.getSection("action"))!!

    init {
        this.updateActionDamage(action, baseUnitStats.damage)
    }

    private fun updateActionDamage(action: IDataAction, baseUnitDamage: Float) {
        if (action is DataSkillLoaded) {
            if (action.damage <= 0) {
                action.damage = baseUnitDamage
            } else {
                action.damage = (action.damage * baseUnitDamage)
            }
        } else if (action is DataSkillMixLoaded) {
            action.parts.forEach {
                this.updateActionDamage(it.action, baseUnitDamage)
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

    override var walkSpeedMax: Float = MConstants.UNIT_AVG_WALK_SPEED_MAX * if (config.contains("walkSpeedMax")) {
        config.getFloat("walkSpeedMax")
    } else {
        armor.walkSpeedFactor * if (animation.animationType == MWEntityAnimationType.RIDER) {
            MConstants.UNIT_RIDER_SPEED_FACTOR
        } else {
            1f
        } * baseUnitStats.walkSpeedFactor
    }

    override var walkAcceleration: Float = MConstants.UNIT_AVG_WALK_ACC * if (config.contains("walkAcceleration")) {
        config.getFloat("walkAcceleration")
    } else {
        armor.walkSpeedFactor * if (animation.animationType == MWEntityAnimationType.RIDER) {
            MConstants.UNIT_RIDER_ACC_FACTOR
        } else {
            1f
        } * baseUnitStats.accelerationFactor
    }

}
