package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.manawars.enums.MWEntityAnimationType

interface IDataActionProperties {


    /**
     * Returns the mana cost of the action. Entities need to spend this amount of mana in order to execute it.
     * They regenerate mana within a certain time.
     * @return mana cost.
     */
    val manaCost: Int


    val rangeMax: Map<MWEntityAnimationType, Int>
    val rangeMin: Map<MWEntityAnimationType, Int>

}