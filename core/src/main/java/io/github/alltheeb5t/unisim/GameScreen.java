package io.github.alltheeb5t.unisim;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.Viewport;

import io.github.alltheeb5t.unisim.map_objects.MapBuilding;

public class GameScreen extends ScreenAdapter {

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private World world;
    private Stage stage;
    private Box2DDebugRenderer box2dDebugRenderer;

    private CampusMap campusMap;

    public GameScreen (OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;

        batch = new SpriteBatch();
        world = new World(new Vector2(0, 0), false);
        stage = new Stage(viewport);

        box2dDebugRenderer = new Box2DDebugRenderer();

        // Custom Logic Component Instantiations
        campusMap = new CampusMap(camera, stage, world);

        Gdx.input.setInputProcessor(campusMap.getInputSystem()); // Inputs related to drag are manually passed to stage
        
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(118/255f,184/255f, 135/255f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        // This is where we would render any static objects
        batch.end();

        box2dDebugRenderer.render(world, camera.combined.scl(1));
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        campusMap.getInputSystem().resize(width, height);
    }


    
}
