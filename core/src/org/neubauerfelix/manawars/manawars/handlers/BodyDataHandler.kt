package org.neubauerfelix.manawars.manawars.handlers

import org.neubauerfelix.manawars.game.AManaWars
import org.neubauerfelix.manawars.game.ILoadableContent
import org.neubauerfelix.manawars.manawars.MManaWars
import org.neubauerfelix.manawars.manawars.entities.animation.human.BodyDataHuman
import org.neubauerfelix.manawars.manawars.entities.animation.human.IBodyDataHuman
import org.neubauerfelix.manawars.manawars.entities.animation.mount.BodyDataMount
import org.neubauerfelix.manawars.manawars.entities.animation.mount.IBodyDataMount
import org.neubauerfelix.manawars.manawars.enums.MWShield
import org.neubauerfelix.manawars.manawars.storage.Configuration
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration

import java.util.HashMap

class BodyDataHandler: IBodyDataHandler, ILoadableContent {

    private val bodyDataHuman = HashMap<String, IBodyDataHuman>()
    private val bodyDataMount = HashMap<String, IBodyDataMount>()


    fun putBodyDataHuman(name: String, shieldType: MWShield?, defaultScale: Float): IBodyDataHuman {
        val skin = BodyDataHuman(name, AManaWars.m.getImageHandler().getTextureRegionMain("char.skin.$name"), shieldType, defaultScale)
        bodyDataHuman[name] = skin
        return skin
    }
    fun putBodyDataMount(name: String, defaultScale: Float): IBodyDataMount {
        val skin = BodyDataMount(name, AManaWars.m.getImageHandler().getTextureRegionMain("mount.skin.$name"), defaultScale)
        bodyDataMount[name] = skin
        return skin
    }

    override fun getBodyDataHuman(name: String): IBodyDataHuman {
        assert(bodyDataHuman.containsKey(name))
        return bodyDataHuman[name]!!
    }

    override fun getBodyDataMount(name: String): IBodyDataMount {
        assert(bodyDataMount.containsKey(name))
        return bodyDataMount[name]!!
    }

    override fun loadContent(gameConfig: Configuration) {
        val handlerConfigNames = gameConfig.getStringList("bodyData")
        for(handlerConfigName in handlerConfigNames){
            val handlerConfig = YamlConfiguration.getProvider(YamlConfiguration::class.java).load(handlerConfigName, true)
            for(bodyData in handlerConfig.getStringList("bodyDataHuman")){
                val parts = bodyData.split(":")
                val skinName = parts[0]
                val shieldName = parts[1]
                val defaultScale = if(parts.size >= 3) parts[2].toFloat() else 1f
                val shieldType = if (shieldName.equals("none", ignoreCase = true)) null else MManaWars.m.getShieldHandler().getShield(shieldName)
                putBodyDataHuman(skinName, shieldType, defaultScale)
            }
            for(bodyData in handlerConfig.getStringList("bodyDataMount")){
                val parts = bodyData.split(":")
                val skinName = parts[0]
                val defaultScale = if(parts.size >= 2) parts[1].toFloat() else 1f
                putBodyDataMount(skinName, defaultScale)
            }
        }
    }
}
