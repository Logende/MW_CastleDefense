package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.JsonConfiguration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

class Evaluation {

    companion object {

        const val ADVANCED_EVALUATION = true

        fun startEvaluationScreen(game: MManaWars, args: Array<String>) {
            val inputConfigPath = if (args.size >= 2) {
                args[1]
            } else {
                "evaluation_config.yml"
            }

            val outputConfigPath = if (args.size >= 3) {
                args[2]
            } else {
                "evaluation_results.json"
            }

            game.startScreen(EvaluationScreen(game, readConfig(inputConfigPath), outputConfigPath), true)
        }

        // TODO: Read config instead from file
        fun readConfig(inputConfigPath: String) : IEvaluationConfig {
            println("Reading Evaluation config file from path '$inputConfigPath'.")
            val config = ConfigurationProvider.getProvider(YamlConfiguration::class.java).
                    load(inputConfigPath, false)
            return EvaluationConfigLoaded(config)
        }

        fun writeResults(results: Iterable<IEvaluationResult>, outputConfigPath: String) {
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
            println("Writing Evaluation result file to path '$outputConfigPath'.")
            ConfigurationProvider.getProvider(JsonConfiguration::class.java).save(config, outputConfigPath, false)
        }
    }
}