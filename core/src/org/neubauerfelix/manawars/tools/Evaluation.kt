package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class Evaluation {

    companion object {

        const val CONFIG_NAME = "evaluation_config.yml"
        const val RESULTS_NAME = "evaluation_results.yml"
        const val ADVANCED_EVALUATION = true

        fun startEvaluationScreen(game: MManaWars) {
            game.startScreen(EvaluationScreen(game, readConfig()), true)
        }

        // TODO: Read config instead from file
        fun readConfig() : IEvaluationConfig {
            val config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).
                    load(CONFIG_NAME, false)
            return EvaluationConfigLoaded(config)
        }

        fun writeResults(results: Iterable<IEvaluationResult>) {
            val config = Configuration()
            var i = 0
            for (result in results) {
                config.set("$i.tribeA", result.match.tribeA.name)
                config.set("$i.tribeB", result.match.tribeB.name)
                config.set("$i.controllerA", result.match.controllerA.name)
                config.set("$i.controllerB", result.match.controllerB.name)
                config.set("$i.duration", result.matchDuration)
                config.set("$i.victoryType", result.victoryType.name)
                if (result is IEvaluationResultAdvanced) {
                    config.set("$i.events", result.events)
                }

                i++
            }
            ConfigurationProvider.getProvider(YamlConfiguration::class.java).save(config, RESULTS_NAME, false)
        }
    }
}