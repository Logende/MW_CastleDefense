package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class Evaluation {

    companion object {

        const val CONFIG_NAME = "evaluation_config.yml"

        fun startEvaluationScreen(game: MManaWars) {
            game.startScreen(EvaluationScreen(game, readConfig()), true)
        }

        // TODO: Read config instead from file
        fun readConfig() : IEvaluationConfig {
            val config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).
                    load(CONFIG_NAME, false)
            return EvaluationConfigLoaded(config)
        }
    }
}