package io.github.alltheeb5t.unisim.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.github.alltheeb5t.unisim.Constants;
import io.github.alltheeb5t.unisim.map_objects.MapBuilding;

public class MapInputSystem implements InputProcessor{

    private OrthographicCamera camera;
    private Stage stage;
    private DragAndDrop dragAndDrop;

    public MapInputSystem(OrthographicCamera camera, Stage stage) {
        this.camera = camera;
        this.stage = stage;
        this.dragAndDrop = new DragAndDrop();
    }

    // ─── Building Movement ───────────────────────────────────────────────

    public void registerDraggableBuilding(MapBuilding building) {
        // Drag and drop listener for test building
        dragAndDrop.addSource(new DragAndDrop.Source(building) {
            @Override
			public DragAndDrop.Payload dragStart(InputEvent inputEvent, float v, float v1, int i) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
				payload.setDragActor(getActor());
                stage.addActor(building);
				dragAndDrop.setDragActorPosition(getActor().getWidth() / 2, -getActor().getHeight() / 2); // Move image so the cursor is in the centre of the object during dragging

				return payload;
			}

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
				System.out.println("Debug: Drag stop");
			}
        });
    }


    // ─── Map Pan And Zoom ────────────────────────────────────────────────

    // Used by to track how much the cursor has moved between two 'touchDragged' events.
    // The camera is then translated by this.
    private int panStartX;
    private int panStartY;

    /** 
     * Moves the camera by a set amount but prevents going beyond map boundaries
     */
    private void clampedTranslate(float x, float y) {
        camera.position.x += x;
        camera.position.y += y;

		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        // Ensure that the camera's viewport never goes beyond the edge of the map
        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, Constants.CAMPUS_MAX_X - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, Constants.CAMPUS_MAX_Y - effectiveViewportHeight / 2f);
		
	}

    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }
    
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        panStartX = screenX;
        panStartY = screenY;
        return stage.touchDown(screenX, screenY, pointer, button);
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
        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE))  {
            clampedTranslate((panStartX-screenX), -(panStartY-screenY));
            panStartX = screenX;
            panStartY = screenY;
        }
        
        // Input handler would ordinarily be set to the stage. Now we've finished, pass input events to stage.
        return stage.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean touchUp (int screenX, int screenY, int pointer, int button) {
        return stage.touchUp(screenX, screenY, pointer, button);
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
        // We find the in-game coordinates of the cursor initially
        Vector3 cursorPosMeters = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        camera.zoom += amountY/10;

        // Minimum zoom is the entire viewport filling the screen and the maximum is an arbitrary 0.1 (On a 1080p monitor this would be ~98m)
		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, Math.min(Constants.CAMPUS_MAX_X/camera.viewportWidth, Constants.CAMPUS_MAX_Y/camera.viewportHeight));

        camera.update();

        // Get the in-game coordinates of the cursor now the zoom has been executed
        Vector3 newCursorPosMeters = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        
        // To make zoom natural, whatever is directly under the cursor should stay in the same place
        clampedTranslate(cursorPosMeters.x-newCursorPosMeters.x,cursorPosMeters.y-newCursorPosMeters.y);
        camera.update();
		return true;
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
