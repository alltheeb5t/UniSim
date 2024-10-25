package io.github.alltheeb5t.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.alltheeb5t.unisim.map_objects.MapBuilding;

import io.github.alltheeb5t.unisim.Constants;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Stage stage;
    private Viewport viewport;
    private Box2DDebugRenderer box2dDebugRenderer;
    private MapBuilding testBuilding;

    private DragAndDrop dragAndDrop;

    public GameScreen (OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        box2dDebugRenderer = new Box2DDebugRenderer();

        stage = new Stage(viewport);
        dragAndDrop = new DragAndDrop();
        testBuilding = new MapBuilding(480, 100, 120, world, new Texture("piazza.png"));
        stage.addActor(testBuilding);
        Gdx.input.setInputProcessor(stage);

        dragAndDrop.addSource(new DragAndDrop.Source(testBuilding) {
            @Override
			public DragAndDrop.Payload dragStart(InputEvent inputEvent, float v, float v1, int i) {
                System.out.println("Drag start");
				DragAndDrop.Payload payload = new DragAndDrop.Payload();
				payload.setDragActor(getActor());
                stage.addActor(testBuilding);
				dragAndDrop.setDragActorPosition(getActor().getWidth() / 2, -getActor().getHeight() / 2);

				return payload;
			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
				System.out.println("Drag stop");
			}
        });
    }

    @Override
    public void render(float delta) {
        handleInput();
        camera.update();

        Gdx.gl.glClearColor(118/255f,184/255f, 135/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        //testBuilding.render(batch);
        //System.out.println(camera.view);
        batch.end();
        box2dDebugRenderer.render(world, camera.combined.scl(1));
        stage.draw();

    }

    /**java */
    private void handleInput() {
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			camera.zoom += 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
			camera.zoom -= 0.02;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			camera.translate(-3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			camera.translate(3, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			camera.translate(0, -3, 0);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			camera.translate(0, 3, 0);
		}

        // Minimum zoom is the entire viewport filling the screen and the maximum is an arbitrary 0.1 (On a 1080p monitor this would be ~98m)
		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, Constants.CAMPUS_MAX_X/camera.viewportWidth);

		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        // Ensure that the camera's viewport never goes beyond the edge of the map
		camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, Constants.CAMPUS_MAX_X - effectiveViewportWidth / 2f);
		camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, Constants.CAMPUS_MAX_Y - effectiveViewportHeight / 2f);
	}

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
    
}
