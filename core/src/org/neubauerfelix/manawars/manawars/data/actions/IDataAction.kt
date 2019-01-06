package org.neubauerfelix.manawars.manawars.data.actions

import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.manawars.enums.MWAnimationTypeBodyEffect
import org.neubauerfelix.manawars.manawars.enums.MWRarity
import org.neubauerfelix.manawars.manawars.enums.MWWeaponType


/**
 * Basic interface for actions which entities can execute.
 * @author Felix Neubauer
 */
interface IDataAction : IDataPresentable, IAsset {


    /**
     * Returns the internal action name which for example is used in save files or translation files.
     * Must contain a prefix describing the action type followed by the action name.
     * @return internal skill name.
     */
    val name: String


    /**
     * Returns the action display name shown to the current player (in proper language).
     * @return action display name.
     */
    val displayName: String

    /**
     * Returns the path of the preview texture.
     * @return preview texture path.
     */
    val previewTexturePath: String

    /**
     * Returns the mana cost of the action. Entities need to spend this amount of mana in order to execute it.
     * They regenerate mana within a certain time.
     * @return mana cost.
     */
    val manaCost: Int

    /**
     * Returns the short animation played when the action is executed.
     * @return short animation. Can be `null`.
     */
    val animationEffect: MWAnimationTypeBodyEffect?


    /**
     * Returns the weapontype used by the short animation played when the action is executed.
     * @return weapon type. Can be null.
     */
    val weaponType: MWWeaponType?




    /**
     * Returns all actions this actions depends on. When the action is loaded those actions are loaded as well.
     * @return action dependencies.
     */
    val actionDependencies: Array<IDataAction>


    val rangeMax: Int
    val rangeMin: Int

    /**
     * Executes the defined action.
     * @param owner Entity executing the action.
     * @return 'true' if the action was successful and 'false' if it was not.
     */
    fun action(owner: IEntity): Boolean


    /**
     * Checks whether the entity is currently capable of executing the action.
     * @param entity Entity to check usage-ability.
     * @return `true` if the entity can use the action.
     */
    fun canUse(owner: IEntity): Boolean


}
