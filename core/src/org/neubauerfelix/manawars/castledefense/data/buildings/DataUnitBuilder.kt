package org.neubauerfelix.manawars.castledefense.data.buildings

import org.neubauerfelix.manawars.castledefense.data.actions.DataActionBuild
import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.data.units.DataUnit
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.*
import java.util.*

class DataUnitBuilder(building: IDataBuilding, override val cost: Int, override val rarity: NWRarity) : DataUnit() {

    override val name: String = "builder"
    override val displayName: String = MManaWars.m.getLanguageHandler().getMessage("unit_${name}_name")
    override val unitType: MWUnitType = MWUnitType.BUILDER
    override val animation: IEntityAnimationProducer = IEntityAnimationProducer.createProducerHuman("builder")
    override val armor: MWArmorType = MWArmorType.NONE
    override var actionCooldown: Float = 1f
    override var health: Float = 10f
    override val stateMultipliers: MutableMap<MWState, MWStateEffectivity> = EnumMap(MWState::class.java)
    override val skillMultipliers: MutableMap<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java)
    override val skillDurabilityMultipliers: MutableMap<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java)
    override var drainMultiplier: Float = 0f
    override var walkSpeedMax: Float = MConstants.UNIT_AVG_WALK_SPEED_MAX
    override var walkAcceleration: Float = MConstants.UNIT_AVG_WALK_ACC


    override val action: IDataAction = DataActionBuild(building.name, building)
}