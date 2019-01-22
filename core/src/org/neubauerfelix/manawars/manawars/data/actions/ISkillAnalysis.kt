package org.neubauerfelix.manawars.manawars.data.actions

/**
 * Computed once for every skill by the developer and later stored to/loaded from internal file.
 */
interface ISkillAnalysis {

    val rangeMax: Int
    val rangeMin: Int

    val manaCost: Int

    val lifeTime: Float

    val width: Int
    val height: Int

}