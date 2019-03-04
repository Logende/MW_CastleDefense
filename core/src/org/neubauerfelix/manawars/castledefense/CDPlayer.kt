package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.manawars.data.armies.IDataArmy

class CDPlayer(override val army: IDataArmy, override val controller: ICDController) : ICDPlayer {

    override var gold: Int = 0

}