package io.github.alltheeb5t.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.alltheeb5t.unisim.entities.GameTimerEntity;
import io.github.alltheeb5t.unisim.factories.MapObstructionFactory;
import io.github.alltheeb5t.unisim.map_objects.MapBuilding;
import io.github.alltheeb5t.unisim.systems.GameTimerSystem;
import io.github.alltheeb5t.unisim.systems.MapInputSystem;

public class GameScreen extends ScreenAdapter implements InputProcessor {

    private SpriteBatch batch;
    private Box2DDebugRenderer box2dDebugRenderer;

    private CampusMap campusMap;
    private MapBuilding testBuilding;
    private GameTimerEntity gameTimer;

    public GameScreen (OrthographicCamera camera, Viewport viewport) {
        batch = new SpriteBatch();

        box2dDebugRenderer = new Box2DDebugRenderer();
        campusMap = new CampusMap(camera, new Stage(viewport), new World(new Vector2(0, 0), false), new DragAndDrop());
        testBuilding = MapObstructionFactory.makeMapBuilding(MapObstructionFactory.makeMapObstruction(480, 100, 120, campusMap.getWorld(), new Texture("piazza.png")), campusMap);
        gameTimer = new GameTimerEntity();

        Gdx.input.setInputProcessor(this); // Inputs related to drag are manually passed to stage
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(118/255f,184/255f, 135/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setProjectionMatrix(campusMap.getCamera().combined);
        // This is where we would render any static objects
        batch.end();

        GameTimerSystem.tick(delta, gameTimer);

        box2dDebugRenderer.render(campusMap.getWorld(), campusMap.getCamera().combined.scl(1));
        campusMap.getStage().draw();

    }

    // ─── Map Pan And Zoom ────────────────────────────────────────────────

    public void resize(int width, int height) {
        MapInputSystem.gameScreenResize(width, height, campusMap);
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called automatically when any mouse button is pressed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return MapInputSystem.gameScreenTouchDown(screenX, screenY, pointer, button, campusMap);
    }

    /**
     * Implemented from InputProcessor. Used to handle panning if middle click is pressed
     * @param screenX Pointer X coordinate
     * @param screenY Pointer Y coordinate
     * @param pointer Think this is always 0 for a mouse.
     * @return
     */
    @Override
    public boolean touchDragged (int screenX, int screenY, int pointer) {
        return MapInputSystem.gameScreenDrag(screenX, screenY, pointer, campusMap);
    }

    /**
     * Called automatically when any mouse button is released
     */
    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        return MapInputSystem.gameScreenTouchUp(screenX, screenY, pointer, button, campusMap);
    }

	@Override public boolean keyDown (int keycode) {
		return false;
	}

	@Override public boolean keyUp (int keycode) {
		return false;
	}

	@Override public boolean keyTyped (char character) {
		return false;
	}

    /**
     * Implemented from InputProcessor, used to control Zoom.
     * @param amountX Arbitrary value representing how far the window should move.
     * @param amountY Would be horizontal scrolling. Not used.
     * @return
     */
	@Override public boolean scrolled (float amountX, float amountY) {
        return MapInputSystem.gameScreenScroll(amountY, campusMap);
	}

    /**
     * Implemented as is necessary for a full implementation of InputProcessor. Not relevant on desktop
     * @param screenX
     * @param screenY
     * @param point
     * @param button
     * @return
     */
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }
}
