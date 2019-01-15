package org.neubauerfelix.manawars.game

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.Viewport
import org.neubauerfelix.manawars.game.entities.GameRectangle
import org.neubauerfelix.manawars.game.entities.IEntity
import org.neubauerfelix.manawars.game.entities.ISized

class GameCamera(assetLoader: IAssetLoader): ICamera, IDisposable, IHandler {


    /** Camera for everything within the playground. */
    private var cameraIngame: OrthographicCamera

    /** Camera for static things like menus or receiving the location of touch input. */
    private var cameraStatic: OrthographicCamera

    /** Allows drawing shapes. */
    private val shapeRenderer: ShapeRenderer

    /** Main view. */
    private var viewport: Viewport

    /** Allows drawing images. */
    private val batcher: SpriteBatch

    /** Current zoom (real zoom. Smaller values result in bigger drawings). */
    private val zoomIngame = 1f

    /** This rectangle represents the part of the playground which is currently shown by the camera. */
    private val window: GameRectangle

    private var loadingScreen: TextureRegion

    init {
        cameraIngame = OrthographicCamera()
        cameraIngame.setToOrtho(false, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT)
        cameraIngame.position.set(cameraIngame.viewportWidth / 2f, cameraIngame.viewportHeight / 2f, 0f)

        cameraStatic = OrthographicCamera()
        cameraStatic.setToOrtho(true, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT)
        cameraStatic.position.set(cameraStatic.viewportWidth / 2f, cameraStatic.viewportHeight / 2f, 0f)

        viewport = ExtendViewport(50f, GameConstants.SCREEN_HEIGHT)
        viewport.apply()

        window = GameRectangle(0f, 0f, GameConstants.SCREEN_WIDTH, GameConstants.WORLD_HEIGHT)

        batcher = SpriteBatch()
        batcher.projectionMatrix = cameraIngame.combined

        shapeRenderer = ShapeRenderer()
        shapeRenderer.setAutoShapeType(true)

        assetLoader.loadTexture(GameConstants.PATH_LOADING_SCREEN)
        assetLoader.finishLoading()
        loadingScreen = assetLoader.createTextureRegion(GameConstants.PATH_LOADING_SCREEN)
    }


    override fun render(delta: Float, toDraw1Backgrounds: Iterable<IDrawable>, drawBackgroundsStatic: Boolean, ingameWindowX: Float,
                        toDraw2Ingame: Iterable<IEntity>, toDraw3Components: Iterable<IDrawableComponent>){
        setWindowLocation(ingameWindowX)

        //Make everything ready
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batcher.begin()

        //Draw Backgrounds
        batcher.disableBlending() //fast drawing without transparency
        if (drawBackgroundsStatic) {
            batcher.projectionMatrix = cameraStatic.combined
            synchronized(toDraw1Backgrounds) {
                for (background in toDraw1Backgrounds) {
                    background.draw(delta, batcher)
                }
            }
            batcher.projectionMatrix = cameraIngame.combined
        } else {
            batcher.projectionMatrix = cameraIngame.combined
            synchronized(toDraw1Backgrounds) {
                for (background in toDraw1Backgrounds) {
                    background.draw(delta, batcher)
                }
            }
        }

        //Enable Blending again
        batcher.enableBlending() //drawing with transparency
        synchronized(toDraw2Ingame) {
            for (drawableIngame in toDraw2Ingame) {
                if (drawableIngame is IDrawable) {
                    drawableIngame.draw(delta, batcher)
                }

            }
        }

        batcher.projectionMatrix = cameraStatic.combined
        synchronized(toDraw3Components) {
            for (drawableComponent in toDraw3Components) {
                drawableComponent.draw(delta, batcher, 0f, 0f)
            }
        }
        batcher.end()


        //Draw all Shapes
        shapeRenderer.projectionMatrix = cameraStatic.combined
        shapeRenderer.begin()
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled)
        Gdx.graphics.gL20.glEnable(GL20.GL_BLEND)
        synchronized(toDraw3Components) {
            for (drawableComponent in toDraw3Components) {
                if (drawableComponent is IShapeDrawable) {
                    drawableComponent.draw(delta, shapeRenderer)
                }
            }
        }
        shapeRenderer.end()

        // Debugging Code
        shapeRenderer.projectionMatrix = cameraIngame.combined
        shapeRenderer.begin()
        shapeRenderer.set(ShapeRenderer.ShapeType.Line)
        shapeRenderer.color = Color.RED
        synchronized(toDraw2Ingame) {
            for (drawableIngame in toDraw2Ingame) {
                shapeRenderer.polygon(drawableIngame.polygon.transformedVertices)
            }
        }
        shapeRenderer.end()
    }

    override fun renderLoadingScreen(delta: Float) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 0f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batcher.begin()
        batcher.disableBlending() //fast drawing without transparency
        batcher.draw(loadingScreen, 0f, 0f, GameConstants.SCREEN_WIDTH, GameConstants.SCREEN_HEIGHT)
        batcher.end()
    }

    private fun setWindowLocation(x: Float){
        window.x = x
        cameraIngame.position.set(window.centerHorizontal, window.centerVertical + GameConstants.CONTROLPANEL_HEIGHT * zoomIngame / 2, 0f)
    }

    override fun dispose() {
        batcher.dispose()
        shapeRenderer.dispose()
    }


    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun getViewport(): Viewport {
        return viewport
    }
}