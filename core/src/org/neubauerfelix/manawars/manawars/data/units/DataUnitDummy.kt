package org.neubauerfelix.manawars.manawars.data.units

import org.neubauerfelix.manawars.manawars.MConstants
import org.neubauerfelix.manawars.manawars.data.actions.DataActionNone
import org.neubauerfelix.manawars.manawars.data.actions.IDataAction
import org.neubauerfelix.manawars.manawars.entities.animation.IEntityAnimationProducer
import org.neubauerfelix.manawars.manawars.enums.*
import java.util.*

class DataUnitDummy : DataUnit() {

    override val cost: Int = 0
    override val name: String = "dummy"
    override val displayName: String = "dummy"
    override val animation: IEntityAnimationProducer = IEntityAnimationProducer.createProducerHuman("dummy")
    override val armor: MWArmorType = MWArmorType.NONE

    override val action: IDataAction = DataActionNone()
    override var actionCooldown: Float = 1f
    override var health: Float = Float.MAX_VALUE
    override val stateMultipliers: MutableMap<MWState, MWStateEffectivity> = EnumMap(MWState::class.java)
    override val skillMultipliers: MutableMap<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java)
    override val skillDurabilityMultipliers: MutableMap<MWSkillClass, Float> = EnumMap(MWSkillClass::class.java)
    override var drainMultiplier: Float = 0f
    override var walkSpeedMax: Float = MConstants.UNIT_AVG_WALK_SPEED_MAX * 2f
    override var walkAcceleration: Float = MConstants.UNIT_AVG_WALK_ACC * 3f
    override val unitType: MWUnitType = MWUnitType.MELEE
    override val knockbackFactor: Float = 1f
    override val rarity: NWRarity = NWRarity.COMMON


}