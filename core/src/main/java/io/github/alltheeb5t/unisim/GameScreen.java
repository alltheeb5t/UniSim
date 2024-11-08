package io.github.alltheeb5t.unisim;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.entities.BuildingEntity;
import io.github.alltheeb5t.unisim.entities.CampusMapEntity;
import io.github.alltheeb5t.unisim.entities.LibGdxRenderingEntity;
import io.github.alltheeb5t.unisim.entities.MapObstacleEntity;
import io.github.alltheeb5t.unisim.factories.BuildingFactory;
import io.github.alltheeb5t.unisim.factories.ObstaclesFactory;
import io.github.alltheeb5t.unisim.systems.CampusMapSystem;
import io.github.alltheeb5t.unisim.systems.MapInputSystem;
import io.github.alltheeb5t.unisim.systems.SatisfactionSystem;

public class GameScreen extends ScreenAdapter implements InputProcessor {

    private SpriteBatch batch;
    private static LibGdxRenderingEntity libGdxRenderingEntity;
        public static LibGdxRenderingEntity getLibGdxRenderingEntity() {
            return libGdxRenderingEntity;
    }

    private GUI gui;

    private static CampusMapEntity campusMap;
    public static CampusMapEntity getCampusMap() {
        return campusMap;
    }

    private static List<BuildingEntity> buildings = new LinkedList<>();
        public static List<BuildingEntity> getBuildings() {
            return buildings;
    }

    private List<MapObstacleEntity> obstacles = new LinkedList<>();

    public GameScreen (OrthographicCamera camera, Viewport viewport) {
        batch = new SpriteBatch();

        libGdxRenderingEntity = new LibGdxRenderingEntity(camera, new Stage(viewport), new DragAndDrop());
        campusMap = new CampusMapEntity();

        gui = new GUI();

        obstacles.addAll(ObstaclesFactory.makeMapOrchard(475,200, libGdxRenderingEntity));
        obstacles.addAll(ObstaclesFactory.makeMapOrchard(100,750, libGdxRenderingEntity));
        obstacles.addAll(ObstaclesFactory.makeMapOrchard(650,850, libGdxRenderingEntity));
        obstacles.addAll(ObstaclesFactory.makeMapOrchard(900,550, libGdxRenderingEntity));
        obstacles.add(ObstaclesFactory.makeMapRoad(700, 900, libGdxRenderingEntity));
        obstacles.addAll(ObstaclesFactory.makeMapLake(800, 500, libGdxRenderingEntity, 0));
        obstacles.add(ObstaclesFactory.makeMapRiver(1700, 400, libGdxRenderingEntity));
        obstacles.add(ObstaclesFactory.makeMapBridge(1700, 500, libGdxRenderingEntity));
        obstacles.addAll(ObstaclesFactory.makeMapLake(1675, 125, libGdxRenderingEntity, 1));
        obstacles.addAll(ObstaclesFactory.makeMapMountain(1700, 900, libGdxRenderingEntity));
        obstacles.addAll(ObstaclesFactory.makeMapMountain(1650, 875, libGdxRenderingEntity));
        obstacles.addAll(ObstaclesFactory.makeMapMountain(1785, 850, libGdxRenderingEntity));

        CampusMapSystem.addAllObstaclesToMap(campusMap, obstacles);

        Gdx.input.setInputProcessor(new InputMultiplexer(gui.getStage(), this)); // Inputs related to drag are manually passed to stage
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(118/255f,184/255f, 135/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setProjectionMatrix(libGdxRenderingEntity.getCamera().combined);

        // This is where we would render any static objects
        batch.end();

        libGdxRenderingEntity.getStage().draw();

        // Recalculating satisfaction on every frame is incredibly inefficient. Should really do it on drag end event
        SatisfactionSystem.recalculateBuildingSatisfaction(buildings);

        gui.render();

    }

    // ─── Map Pan And Zoom ────────────────────────────────────────────────

    public void resize(int width, int height) {
        MapInputSystem.gameScreenResize(width, height, libGdxRenderingEntity);
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
        return MapInputSystem.gameScreenTouchDown(screenX, screenY, pointer, button, libGdxRenderingEntity);
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
        return MapInputSystem.gameScreenDrag(screenX, screenY, pointer, libGdxRenderingEntity);
    }

    /**
     * Called automatically when any mouse button is released
     */
    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        return MapInputSystem.gameScreenTouchUp(screenX, screenY, pointer, button, libGdxRenderingEntity);
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
        return MapInputSystem.gameScreenScroll(amountY, libGdxRenderingEntity);
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
