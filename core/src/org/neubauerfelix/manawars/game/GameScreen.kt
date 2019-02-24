package org.neubauerfelix.manawars.game

import com.badlogic.gdx.InputProcessor
import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector3
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ILogicable
import org.neubauerfelix.manawars.game.entities.IMovable

open abstract class GameScreen(game: AManaWars, drawBackgroundsStatic: Boolean): IScreen, InputProcessor {

    private val game: AManaWars = game

    private var deltaStored = 0f
    private var state: ScreenState = ScreenState.WAITING
    private var timeSpeedModifier = 1f

    private val entities: MutableList<IEntity> = ArrayList()
    private val components: MutableList<IComponent> = ArrayList()
    private val backgrounds: MutableList<IDrawable> = ArrayList()
    private val drawBackgroundsStatic: Boolean = drawBackgroundsStatic

    private var notifyAssetsLoaded: Boolean = true

    private val camera: Camera
    private var touch = Vector3()


    init {
        camera = OrthographicCamera()
        camera.setToOrtho(false, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT)
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f)
    }


    override fun render(delta: Float) {
        if(state != ScreenState.RUNNING){
            if(state == ScreenState.LOADING){
                if(game.getAssetLoader().areAssetsLoaded()){
                    loaded()
                    state = ScreenState.RUNNING
                }
            }
            game.getCamera().renderLoadingScreen(delta)
            return
        }

        if(notifyAssetsLoaded){
            if(game.getAssetLoader().areAssetsLoaded()){
                game.loadedAssets()
                notifyAssetsLoaded = false
            }
        }

        deltaStored += delta
        var delta = 0f
        if(deltaStored >= GameConstants.GAME_RENDER_FIX_TIME_STEPS_DURATION){
            deltaStored -= GameConstants.GAME_RENDER_FIX_TIME_STEPS_DURATION
            delta = GameConstants.GAME_RENDER_FIX_TIME_STEPS_DURATION
        }
        delta *= timeSpeedModifier

        synchronized(entities){
            var last = entities.size-1
            var i = 0
            while(i <= last){
                val entity = entities.get(i)
                if(entity.remove){
                    entities.removeAt(i)
                    last--
                    continue
                }
                if(entity is ILogicable){
                    entity.doLogic(delta)
                }
                if(entity is IMovable){
                    entity.move(delta)
                }
                i++
            }
        }
        this.logic(delta, entities)
        game.getCamera().render(delta, backgrounds, drawBackgroundsStatic, getIngameWindowX(),
                entities, components)
    }



    @Synchronized override fun load() {
        check(state == ScreenState.WAITING)
        state = ScreenState.LOADING
        println("Loading screen.")
        if(loadScreen()){
            loaded()
        }
    }


    @Synchronized open fun loaded() {
        check(state == ScreenState.LOADING)
        loadedScreen()
        state = ScreenState.RUNNING
        println("Loaded screen.")
    }

    @Synchronized override fun dispose() {
        check(state == ScreenState.RUNNING || state == ScreenState.PAUSED || state == ScreenState.LOADING)
        state = ScreenState.DISPOSING
        println("Disposing screen.")
        disposeScreen()
        entities.clear()
        components.clear()
        backgrounds.clear()
        println("Disposed screen.")
        state = ScreenState.DISPOSED
    }


    @Synchronized override fun pause() {
        if(state != ScreenState.RUNNING){
            throw RuntimeException("Can not pause screen: Screen in state '" + state.name + "'.")
        }
        state = ScreenState.PAUSED
        unclickAll()
        println("Paused screen.")
    }

    @Synchronized override fun hide() {
    }

    @Synchronized override fun resume() {
        if(state != ScreenState.PAUSED){
            throw RuntimeException("Can not resume screen: Screen in state '" + state.name + "'.")
        }
        state = ScreenState.RUNNING
        println("Resumed screen.")
    }

    /**
     * Called when screen is shown, either when the screen is shown initially or after it has been paused and hidden.
     */
    @Synchronized override fun show() {
    }

    @Synchronized override fun resize(width: Int, height: Int) {
        game.getCamera().resize(width, height)
    }


    override fun getGame(): AManaWars{
        return game
    }

    override fun getState(): ScreenState {
        return state
    }

    fun getTimeSpeedModifier(): Float {
        return timeSpeedModifier
    }

    fun setTimeSpeedModifier(f: Float) {
        timeSpeedModifier = f
    }

    override fun requestNotificationWhenAssetsLoaded() {
        synchronized(notifyAssetsLoaded){
            notifyAssetsLoaded = true
        }
    }


    override fun addEntity(entity: IEntity) {
        entities.add(entity)
    }

    override fun containsEntity(entity: IEntity): Boolean {
        return entities.contains(entity)
    }

    override fun getEntities(condition: (IEntity) -> Boolean): List<IEntity> {
        return entities.filter(condition)
    }

    override fun addBackground(background: IDrawable, backgroundLimit: Int) {
        while(backgrounds.size >= backgroundLimit){
            val backgroundOld = backgrounds[0]
            if(backgroundOld is IDisposable) {
                backgroundOld.dispose()
            }
            backgrounds.removeAt(0)
        }
        if(background is ILoadableAsync){
            if(!background.isLoaded()){
                throw RuntimeException("Background can not be added to screen: Not loaded yet.")
            }
        }
        backgrounds.add(background)
    }

    override fun removeBackground(background: IDrawable) {
        if(!backgrounds.contains(background)){
            throw RuntimeException("Unable to remove background: Not contained in screen.")
        }
        backgrounds.remove(background)
    }

    abstract fun loadScreen(): Boolean
    abstract fun loadedScreen()
    abstract fun disposeScreen()
    abstract fun getIngameWindowX(): Float
    abstract fun logic(delta: Float, entities: List<IEntity>)


    override fun getInputProcessor(): InputProcessor {
        return this
    }

    override fun keyDown(keycode: Int): Boolean {
        return true
    }

    override fun keyUp(keycode: Int): Boolean {
        return true
    }

    override fun keyTyped(character: Char): Boolean {
        return true
    }

    override fun touchDown(screenX: Int, screenY: Int, pointerId: Int, button: Int): Boolean {
        camera!!.unproject(touch.set(screenX.toFloat(), screenY.toFloat(), 0f))
        val x = touch.x
        val y = touch.y
        synchronized(components) {
            for (i in components.indices.reversed()) {
                val c = components[i]
                if (!c.isHidden()) {
                    if (c.touch(x, y, pointerId)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    override fun touchUp(screenX: Int, screenY: Int, pointerId: Int, button: Int): Boolean {
        camera!!.unproject(touch.set(screenX.toFloat(), screenY.toFloat(), 0f))
        val x = touch.x
        val y = touch.y
        synchronized(components) {
            for (i in components.indices.reversed()) {
                val c = components[i]
                if (!c.isHidden()) {
                    if (c.release(x, y, pointerId)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    override fun touchDragged(screenX: Int, screenY: Int, pointerId: Int): Boolean {
        camera!!.unproject(touch.set(screenX.toFloat(), screenY.toFloat(), 0f))
        val x = touch.x
        val y = touch.y
        synchronized(components) {
            for (i in components.indices.reversed()) {
                val c = components[i]
                if (!c.isHidden()) {
                    if (c.drag(x, y, pointerId)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean {
        return false
    }

    override fun scrolled(amount: Int): Boolean {
        return false
    }

    private fun unclickAll() {
        synchronized(components) {
            for (c in components) {
                c.unclick()
            }
        }
    }

    override fun addComponent(component: IComponent) {
        components.add(component)
    }

    override fun removeComponent(component: IComponent) {
        if(!components.contains(component)){
            throw RuntimeException("Unable to remove component: Not contained in screen.")
        }
        components.remove(component)
    }
}