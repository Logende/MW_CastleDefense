package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.IHandler


interface ILanguageHandler: IHandler {

    fun hasMessage(path: String): Boolean
    fun getMessage(path: String): String
    fun transform(input: String): String


}