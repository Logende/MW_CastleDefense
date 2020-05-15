package org.neubauerfelix.manawars.tools

import org.neubauerfelix.manawars.castledefense.ki.CDKIFeatureExtractor
import org.neubauerfelix.manawars.castledefense.ki.machinelearning.CDKIMachineLearning
import org.neubauerfelix.manawars.castledefense.ki.machinelearning.CDKIModelRandom
import org.neubauerfelix.manawars.castledefense.ki.traditional.CDKITraditionalAggressive
import org.neubauerfelix.manawars.castledefense.ki.traditional.CDKITraditionalFelix
import org.neubauerfelix.manawars.castledefense.player.CDControllerBot
import org.neubauerfelix.manawars.castledefense.player.ICDController

enum class ControllerType {

    FELIX {
        override fun produce() : ICDController {
            return CDControllerBot(CDKITraditionalFelix())
        }
    },
    AGGRESSIVE {
        override fun produce() : ICDController {
            return CDControllerBot(CDKITraditionalAggressive())
        }
    },
    RANDOM {
        override fun produce() : ICDController {
            return CDControllerBot(CDKIMachineLearning(CDKIModelRandom(), CDKIFeatureExtractor()))
        }
    },

    ;

    abstract fun produce() : ICDController
}