package io.github.alltheeb5t.unisim;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

/**
 * Used to store key libgdx rendering-related objects.
 */
public class CampusMap {
    private OrthographicCamera camera;
    private Stage stage;
    private World world;
    private DragAndDrop dragAndDrop;

    // Used by drag routines to determine how much the pointer has moved since the routine last ran. Objects can then be translated accordingly
    private int lastTouchX, lastTouchY;

    public CampusMap(OrthographicCamera camera, Stage stage, World world, DragAndDrop dragAndDrop) {
        this.camera = camera;
        this.stage = stage;
        this.world = world;
        this.dragAndDrop = dragAndDrop;
    }

    public Stage getStage() {
        return stage;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public World getWorld() {
        return world;
    }

    public DragAndDrop getDragAndDrop() {
        return dragAndDrop;
    }

    public int getLastTouchX() {
        return lastTouchX;
    }

    public int getLastTouchY() {
        return lastTouchY;
    }

    public void setLastTouch(int x, int y) {
        lastTouchX = x;
        lastTouchY = y;
    }
}
