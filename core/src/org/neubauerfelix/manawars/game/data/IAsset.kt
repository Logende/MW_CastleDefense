package org.neubauerfelix.manawars.game.data

/**
 * Interfaces for plain assets without any surrounding logic. They can be either loaded or unloaded.
 * Loading and unloading works via instance counting: If an asset is for example loaded five times it will remain loaded
 * after disposing it four times and be disposed when disposing it the fifth time.
 * One Asset can be used by multiple DataClasses at the same time.
 *
 * Possible assets:
 * - MSkill images / Skills
 * - Sounds
 * - Music?
 * - Castles and more?
 *
 * What does not need to be loaded/unloaded during the game:
 * - Buttons
 * - Main images including all skins and common game elements
 */
interface IAsset {

    fun loadAsset()
    fun loadedAsset()
    fun disposeAsset()


    // TODO: Rethink this interface. Maybe everything can just be loaded at the start and no dynamic loading is necessary?
    // At least castles, skills, buildings and units should always be loaded

}