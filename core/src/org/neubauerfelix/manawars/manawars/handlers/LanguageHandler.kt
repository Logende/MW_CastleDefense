package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

import java.io.IOException

class LanguageHandler(language: String): ILanguageHandler {

    private lateinit var storageNative: Configuration
    private lateinit var storageEnglish: Configuration

    //Main variables
    lateinit var mainCurrency: String
    lateinit var mainUpgradecurrency: String
    lateinit var mainKills: String
    lateinit var mainStage: String
    lateinit var mainScore: String


    init{
        try {
            storageNative = loadLanguageIndependently(language)
            storageEnglish = if (language.equals("english", ignoreCase = true)) {
                storageNative
            } else {
                loadLanguageIndependently("english")
            }

            mainCurrency = getMessage("main_currency")
            mainUpgradecurrency = getMessage("main_upgradecurrency")
            mainKills = getMessage("main_kills")
            mainStage = getMessage("main_stage")
            mainScore = getMessage("main_score")
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    @Throws(IOException::class)
    fun loadLanguageIndependently(language: String): Configuration {
        return ConfigurationProvider.getProvider(YamlConfiguration::class.java).load("languages/languageset_$language.txt", true)
    }


    override fun getMessage(path: String): String {
        val message = storageNative.getString(path) ?: return getMessageEnglish(path)
        return transform(message)
    }

    private fun getMessageEnglish(path: String): String {
        val message = storageEnglish.getString(path) ?: return path
        return transform(message)
    }


    override fun transform(input: String): String {
        var input = input
        if (input.contains("%")) {
            input = input.replace("%currency%", mainCurrency)
            input = input.replace("%upgradecurrency%", mainUpgradecurrency)
            input = input.replace("%stage%", mainStage)
            input = input.replace("%kills%", mainKills)
            input = input.replace("%score%", mainScore)
            if (input.contains("%upgrademoney%")) {
                //input = input.replace("%upgrademoney%", String.valueOf(ManaWarsGame.g.getActiveProfile().getCrystals())) TODO
            }
            if (input.contains("%money%")) {
                //input = input.replace("%money%", String.valueOf(ManaWarsGame.g.getActiveProfile().getMoney())) TODO
            }
        }
        return input
    }

}
