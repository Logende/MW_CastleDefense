package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.enums.MWShield
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

import java.util.HashMap

class ShieldHandler: IShieldHandler, ILoadableContent {

    private val shields = HashMap<String, MWShield>()
    private lateinit var normalSmall: MWShield
    private lateinit var enchantedSmall: MWShield
    private lateinit var normalBig: MWShield
    private lateinit var enchantedBig: MWShield



    fun putShield(textureName: String, enchanted: Boolean) {
        val shield = MWShield(textureName, enchanted)
        putShield(shield)
    }

    fun putShield(shield: MWShield) {
        shields[shield.textureName.toLowerCase()] = shield
    }

    override fun getShield(name: String): MWShield {
        var name = name.replace("_", ".").toLowerCase()
        if (!shields.containsKey(name)) {
            throw NullPointerException("Shield '$name' not found!")
        }
        return shields[name]!!
    }

    override fun getPlayerShield(enchanted: Boolean, big: Boolean): MWShield {
        return if (enchanted) {
            if (big) enchantedBig else enchantedSmall
        } else {
            if (big) normalBig else normalSmall
        }
    }



    override fun loadContent(gameConfig: Configuration) {
        val handlerConfigNames = gameConfig.getStringList("shields")
        for(handlerConfigName in handlerConfigNames){
            val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load(handlerConfigName, true)
            for(shieldData in handlerConfig.getStringList("shields")){
                val parts = shieldData.split(":")
                val textureName = parts[0]
                val enchanted = parts[1].toBoolean()
                putShield(textureName, enchanted)
            }
            if(handlerConfig.contains("playershields.normal_small")){
                normalSmall = getShield(handlerConfig.getString("playershields.normal_small"))
            }
            if(handlerConfig.contains("playershields.normal_big")){
                normalBig = getShield(handlerConfig.getString("playershields.normal_big"))
            }
            if(handlerConfig.contains("playershields.enchanted_small")){
                enchantedSmall = getShield(handlerConfig.getString("playershields.enchanted_small"))
            }
            if(handlerConfig.contains("playershields.enchanted_big")){
                enchantedBig = getShield(handlerConfig.getString("playershields.enchanted_big"))
            }
        }
        assert(::normalSmall.isInitialized)
        assert(::normalBig.isInitialized)
        assert(::enchantedSmall.isInitialized)
        assert(::enchantedSmall.isInitialized)
    }
}
