package org.neubauerfelix.manawars.castledefense

import org.neubauerfelix.manawars.manawars.data.armies.IDataArmy

interface ICDPlayer {

    val army: IDataArmy
    val controller: ICDController

    var gold: Int
}