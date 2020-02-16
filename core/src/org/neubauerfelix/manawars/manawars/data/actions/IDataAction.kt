package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.analysis.IDataActionProperties
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.*


/**
 * Basic interface for actions which entities can execute.
 * @author Felix Neubauer
 */
interface IDataAction : IDataPresentable, IAsset, IDataActionLook {


    /**
     * Returns all actions this actions depends on. When the action is loaded those actions are loaded as well.
     * @return action dependencies.
     */
    val actionDependencies: Array<IDataAction>




    /**
     * Executes the defined action.
     * @param owner Entity executing the action.
     * @return 'true' if the action was successful and 'false' if it was not.
     */
    fun action(owner: IActionUser): Boolean


    /**
     * Checks whether the entity is currently capable of executing the action.
     * @param entity Entity to check usage-ability.
     * @return `true` if the entity can use the action.
     */
    fun canUse(owner: IActionUser): Boolean



    val rangeMax: Float
    val rangeMin: Float

}
