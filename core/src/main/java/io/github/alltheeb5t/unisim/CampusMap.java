package io.github.alltheeb5t.unisim;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

import io.github.alltheeb5t.unisim.map_objects.MapBuilding;
import io.github.alltheeb5t.unisim.systems.MapInputSystem;

public class CampusMap {
    private MapInputSystem mapInputSystem;

    private OrthographicCamera camera;
    private Stage stage;
    private World world;

    private MapBuilding testBuilding;

    public CampusMap(OrthographicCamera camera, Stage stage, World world) {
        this.camera = camera;
        this.stage = stage;
        this.world = world;

        mapInputSystem = new MapInputSystem(camera, stage);

        // Testing only: Add temporary building
        testBuilding = new MapBuilding(480, 100, 120, world, new Texture("piazza.png"));
        stage.addActor(testBuilding);

        mapInputSystem.registerDraggableBuilding(testBuilding);
    }

    public MapInputSystem getInputSystem() {
        return mapInputSystem;
    }
}
