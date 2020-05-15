package org.neubauerfelix.manawars.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import org.neubauerfelix.manawars.game.events.EventHandler
import org.neubauerfelix.manawars.manawars.storage.ConfigurationProvider
import org.neubauerfelix.manawars.manawars.storage.YamlConfiguration
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

abstract class GameManaWars: AManaWars(){

    private val handlers: MutableMap<String, IHandler> = LinkedHashMap()
    private val handlersOrder: MutableList<String> = ArrayList()
    private val screens: Stack<IScreenTimed> = Stack()
    private var loaded: Boolean = false

    override fun create() {
        AManaWars.init(this)
        this.load()
    }

    override fun load(){
        loadHandler(GameAssetLoader(), IAssetLoader::class.java)
        if (GameConstants.DISABLE_GRAPHICS) {
            loadHandler(GameCameraMock(), ICamera::class.java)
        } else {
            loadHandler(GameCamera(getAssetLoader()), ICamera::class.java)
        }
        loadHandler(GameImageHandler(getAssetLoader()), IImageHandler::class.java)
        loadHandler(EventHandler(), IEventHandler::class.java)
        loadGame()
    }

    fun <T: IHandler> loadHandler(handler: IHandler, key: Class<T>){
        if(handler is ILoadable){
            handler.load()
        }
        handlersOrder.add(key.name)
        handlers.put(key.name, handler)
    }

    override fun loadedAssets() {
        synchronized(handlers){
            for(handler in handlers.values){
                if(handler is ILoadableAsync){
                    handler.loadedAssets()
                }
            }
            val gameConfig = ConfigurationProvider.getProvider(YamlConfiguration::class.java).load("content/content.yml", true)
            for(handler in handlers.values){
                if(handler is ILoadableContent){
                    handler.loadContent(gameConfig)
                }
            }
        }
        if(!loaded){
            loadedGame()
            loaded = true
        }
    }


    override fun dispose() {
        super.setScreen(null)
        synchronized(screens){
            for(screen in screens){
                screen.dispose()
            }
            screens.clear()
        }
        synchronized(handlers){
            for(i in handlersOrder.size-1 downTo 0){
                val s = handlersOrder.get(i)
                val handler = handlers.get(s)
                if(handler is IDisposable){
                    handler.dispose()
                }
            }
            handlersOrder.clear()
            handlers.clear()
        }

    }
    override fun reset() {
        synchronized(handlers) {
            for (handler in handlers.values) {
                if (handler is IResetable) {
                    handler.reset()
                }
            }
        }
    }

    fun <T: IHandler> getHandler(clazz: Class<T>): T {
        val handler = handlers.get(clazz.name)
        return clazz.cast(handler)
    }

    override fun getCamera(): ICamera {
        return getHandler(ICamera::class.java)
    }

    override fun getEventHandler(): IEventHandler {
        return getHandler(IEventHandler::class.java)
    }

    override fun getAssetLoader(): IAssetLoader {
        return getHandler(IAssetLoader::class.java)
    }

    override fun getImageHandler(): IImageHandler {
        return getHandler(IImageHandler::class.java)
    }

    @Deprecated(level = DeprecationLevel.ERROR,
            message = "this is a libGDX method that should not be called directly.",
            replaceWith = ReplaceWith(
                    expression = "startScreen(screen, unloadCurrent)")
    )
    override fun setScreen(screen: Screen) {
        super.setScreen(screen)
    }

    override fun getScreen(): IScreenTimed {
        return screens.peek()
    }

    override fun startScreen(screen: IScreenTimed, unloadCurrent: Boolean) {
        synchronized(screens){
            if(!screens.empty()){
                if(unloadCurrent){
                    screens.peek().dispose()
                    screens.pop()
                }else{
                    screens.peek().pause()
                }
            }
            screens.push(screen)
            screen.load()
            Gdx.input.inputProcessor = screen.getInputProcessor()
            super.setScreen(screen)
        }
    }

    override fun stopScreen() {
        synchronized(screens){
            if(screens.size < 2){
                throw RuntimeException("Can not stop screen: Not at least two screens running.")
            }
            val current = screens.peek()
            current.dispose()
            screens.pop()
            val next = screens.peek()
            Gdx.input.inputProcessor = next.getInputProcessor()
            super.setScreen(next)
            next.resume()
        }
    }

    abstract fun loadGame()
    abstract fun loadedGame()


}