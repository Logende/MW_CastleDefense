package org.neubauerfelix.manawars.castledefense.handlers

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.files.FileHandle
import org.neubauerfelix.manawars.castledefense.CDConstants
import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.castledefense.profile.PlayerProfile
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class ProfileHandler : IProfileHandler, ILoadableContent {

    private lateinit var playerProfile: PlayerProfile
    override var loadedContent: Boolean = false


    override fun loadContent(gameConfig: Configuration) {
        if (!loadedContent) {
            loadedContent = true

            val yamlProvider = YamlConfiguration.getProvider(YamlConfiguration::class.java)

            val fileHandle = Gdx.files.external(CDConstants.PLAYER_PROFILE_FILE_NAME)
            if (!fileHandle.exists()) {
               // TODO: Create default config file
                val config = Configuration()
                yamlProvider.save(config, fileHandle)
            }

            val profileConfig = yamlProvider.load(fileHandle)
            playerProfile = PlayerProfile((profileConfig))
        }
    }

    override fun getProfile(): PlayerProfile {
        return playerProfile
    }

    // TODO: Save profile when disposing and also during game
}