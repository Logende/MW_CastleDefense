package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.castledefense.ki.CDKIFeatureExtractor
import org.neubauerfelix.manawars.castledefense.ki.machinelearning.CDKIMachineLearning
import org.neubauerfelix.manawars.castledefense.ki.machinelearning.CDKIModelRandom
import org.neubauerfelix.manawars.castledefense.ki.traditional.CDKITraditionalAggressive
import org.neubauerfelix.manawars.castledefense.ki.traditional.CDKITraditionalFelix
import org.neubauerfelix.manawars.castledefense.ki.traditional.CDKITraditionalRPS
import org.neubauerfelix.manawars.castledefense.player.CDControllerBot
import org.neubauerfelix.manawars.castledefense.player.CDControllerHuman
import org.neubauerfelix.manawars.castledefense.player.ICDController

enum class CDControllerType {

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
    RPS {
        override fun produce() : ICDController {
            return CDControllerBot(CDKITraditionalRPS())
        }
    },
    RANDOM {
        override fun produce() : ICDController {
            return CDControllerBot(CDKIMachineLearning(CDKIModelRandom(), CDKIFeatureExtractor()))
        }
    },
    HUMAN {
        override fun produce() : ICDController {
            return CDControllerHuman()
        }
    },

    ;

    abstract fun produce() : ICDController
}