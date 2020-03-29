package org.neubauerfelix.manawars.manawars.handlers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.neubauerfelix.manawars.manawars.data.DataBackground
import org.neubauerfelix.manawars.manawars.data.IDataBackground

internal class BackgroundComposerTest {

    val composer = BackgroundComposer()

    @Test
    fun testMatchBackgrounds() {
        val b_0_0_0 = DataBackground("0_0_0", false)
        val b_0_1_0 = DataBackground("0_1_0", false)
        val b_1_1_0 = DataBackground("1_1_0", false)
        val remaining = mutableListOf<List<IDataBackground>>()
        remaining.add(listOf(b_0_0_0))
        remaining.add(listOf(b_0_0_0, b_0_1_0, b_1_1_0))
        remaining.add(listOf(b_1_1_0))
        val result = ArrayList<IDataBackground>() // mutable! and will be changed by the following function
        composer.matchBackgrounds(result, remaining)
        assertIterableEquals(listOf(b_0_0_0, b_0_1_0, b_1_1_0), result)
    }

    @Test
    fun validCombination() {
        val b_0_0_0 = DataBackground("0_0_0", false)
        val b_0_0_3 = DataBackground("0_0_3", false)
        val b_0_1_0 = DataBackground("0_1_0", false)
        val b_1_1_0 = DataBackground("1_1_0", false)
        val b_1_0_0 = DataBackground("0_1_0", true)

        // valid same themes same background
        assert(composer.validCombination(b_0_0_0, b_0_0_0))

        // valid same themes other background
        assert(composer.validCombination(b_0_0_0, b_0_0_3))

        // invalid different themes
        assertFalse(composer.validCombination(b_0_0_0, b_1_1_0))

        // valid crossover
        assert(composer.validCombination(b_0_0_0, b_0_1_0))

        // invalid crossover
        assertFalse(composer.validCombination(b_0_0_0, b_1_0_0))
    }
}