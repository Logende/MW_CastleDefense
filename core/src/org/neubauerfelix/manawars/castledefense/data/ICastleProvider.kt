package org.neubauerfelix.manawars.castledefense.data

import org.neubauerfelix.manawars.castledefense.data.tribes.IDataCastle

interface ICastleProvider {

    fun getCastle(name: String): IDataCastle?
}