package io.github.alltheeb5t.unisim.entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

/**
 * This Entity stores standard LibGdx components. This class is used extensively by MapInputSystem
 */
public class LibGdxRenderingEntity {
    private OrthographicCamera camera;
    private Stage stage;
    private DragAndDrop dragAndDrop;

    // Used by drag routines to determine how much the pointer has moved since the routine last ran. Objects can then be translated accordingly
    private int lastTouchX, lastTouchY;

    public LibGdxRenderingEntity(OrthographicCamera camera, Stage stage, DragAndDrop dragAndDrop) {
        this.camera = camera;
        this.stage = stage;
        this.dragAndDrop = dragAndDrop;
    }

    public Stage getStage() {
        return stage;
    }

    public OrthographicCamera getCamera() {
        return camera;
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

    /**
     * While in the process of dragging an item, every time the element is successfully re-drawn, this is called.
     * @param x
     * @param y
     */
    public void setLastTouch(int x, int y) {
        lastTouchX = x;
        lastTouchY = y;
    } 
}
