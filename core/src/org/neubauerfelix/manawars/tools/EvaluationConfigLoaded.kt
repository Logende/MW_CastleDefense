package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.castledefense.CDManaWars
import org.neubauerfelix.manawars.castledefense.CDMatchConfiguration
import org.neubauerfelix.manawars.castledefense.data.tribes.IDataTribe
import org.neubauerfelix.manawars.manawars.storage.Configuration
import java.util.*

class EvaluationConfigLoaded(config: Configuration) : IEvaluationConfig {
    override val maxMatchLength: Long = config.getLong("maxMatchLength")
    override val maxUnitsCount: Int = config.getInt("maxUnitsCount")

    override val matches: List<CDMatchConfiguration>



    init {
        matches = mutableListOf()

        val matchDefinitions = config.getStringList("matches")
        val tribeHandler = CDManaWars.cd.getTribeHandler()
        for (matchDefinition in matchDefinitions) {
            val parts = matchDefinition.split(":")
            val tribeDef = parts[0]
            val controllerDef = parts[1]

            if (tribeDef.equals("all", ignoreCase = true)) {
                for (tribeA in tribeHandler.listTribes()) {
                    for (tribeB in tribeHandler.listTribes()) {
                        if (tribeA != tribeB && matches.none { it.tribeA == tribeB && it.tribeB == tribeA }) {
                            addMatch(tribeA, tribeB, controllerDef, matches)
                        }
                    }
                }

            } else {
                val tribeParts = tribeDef.split("-")
                val tribeA = tribeHandler.getTribe(tribeParts[0])!!
                val tribeB = tribeHandler.getTribe(tribeParts[1])!!
                addMatch(tribeA, tribeB, controllerDef, matches)
            }
        }
    }



    companion object {
        private fun addMatch(tribeA: IDataTribe, tribeB: IDataTribe, controllerDefinition: String,
                             matches: MutableList<CDMatchConfiguration>) {
            if (controllerDefinition.equals("all", ignoreCase = true)) {
                for (controllerTypeA in ControllerType.values()) {
                    for (controllerTypeB in ControllerType.values()) {
                        matches.add(CDMatchConfiguration(
                                controllerTypeA.produce(),
                                controllerTypeB.produce(),
                                tribeA,
                                tribeB
                        ))
                    }
                }
            } else {
                val controllerParts = controllerDefinition.split("-")
                val controllerTypeA = ControllerType.valueOf(controllerParts[0].toUpperCase(Locale.getDefault()))
                val controllerTypeB = ControllerType.valueOf(controllerParts[0].toUpperCase(Locale.getDefault()))
                matches.add(CDMatchConfiguration(
                        controllerTypeA.produce(),
                        controllerTypeB.produce(),
                        tribeA,
                        tribeB
                ))
            }
        }
    }

}