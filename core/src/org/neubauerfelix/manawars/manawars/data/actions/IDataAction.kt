package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.*


/**
 * Basic interface for actions which entities can execute.
 * @author Felix Neubauer
 */
interface IDataAction : IDataPresentable {


    /**
     * Returns the internal action name which for example is used in save files or translation files.
     * Must contain a prefix describing the action type followed by the action name.
     * @return internal skill name.
     */
    val name: String


    val displayColor: Color


    /**
     * Returns the action display name shown to the current tribe (in proper language).
     * @return action display name.
     */
    val displayName: String

    /**
     * Returns the path of the preview texture.
     * @return preview texture path.
     */
    val previewTexturePath: String
    // TODO: allow animationProducer as alternative of texture path


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


    val soundPath: String?


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
