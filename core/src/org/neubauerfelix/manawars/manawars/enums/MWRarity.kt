package org.neubauerfelix.manawars.manawars.enums

enum class MWRarity private constructor(val chance: Float, val minStage: Int) {

    IMPOSSIBLE(0f, Integer.MAX_VALUE),
    COMMON(1f, 0),
    RARE(0.5f, 6),
    VERY_RARE(0.35f, 12),
    LEGENDARY(0.2f, 18)

}
