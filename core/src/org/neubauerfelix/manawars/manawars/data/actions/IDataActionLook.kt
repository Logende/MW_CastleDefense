package org.neubauerfelix.manawars.manawars.data.actions

import com.badlogic.gdx.graphics.Color
import org.neubauerfelix.manawars.game.data.IAsset
import org.neubauerfelix.manawars.manawars.analysis.IDataActionProperties
import org.neubauerfelix.manawars.manawars.entities.IActionUser
import org.neubauerfelix.manawars.manawars.enums.*


interface IDataActionLook : IDataPresentable, IAsset {


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



}
