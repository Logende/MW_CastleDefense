package org.neubauerfelix.manawars.manawars.enums


enum class MWSkillEffectivity  constructor(val damageFactor: Float) {
    IMMUNE(0f),
    SUPER_WEAK(0.2f),
    WEAK(0.5f),
    NORMAL(1f),
    EFFECTIVE(1.5f),
    SUPER_EFFECTIVE(2f)
}
