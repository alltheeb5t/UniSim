package io.github.alltheeb5t.unisim.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.github.alltheeb5t.unisim.CampusMap;
import io.github.alltheeb5t.unisim.Constants;
import io.github.alltheeb5t.unisim.map_objects.MapObstruction;

public class MapInputSystem {

    // ─── Building Movement ───────────────────────────────────────────────

    public static void registerDraggableObstruction(DragAndDrop dragAndDrop, Stage stage, MapObstruction obstruction) {
        // Drag and drop listener for test building
        dragAndDrop.addSource(new DragAndDrop.Source(obstruction.getImageObject()) {
            @Override
			public DragAndDrop.Payload dragStart(InputEvent inputEvent, float v, float v1, int i) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
				payload.setDragActor(getActor());
                stage.addActor(obstruction.getImageObject());
				dragAndDrop.setDragActorPosition(getActor().getWidth() / 2, -getActor().getHeight() / 2); // Move image so the cursor is in the centre of the object during dragging

				return payload;
			}

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                BuildingSystem.syncBodyPosition(obstruction);
            }

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
				System.out.println("Debug: Drag stop");
			}
        });
    }

    // ─── Map Pan And Zoom Handling ───────────────────────────────────────

    /** 
     * Moves the camera by a set amount but prevents going beyond map boundaries
     */
    private static void clampedTranslate(float x, float y, OrthographicCamera camera) {
        camera.position.x += x;
        camera.position.y += y;

		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        // Ensure that the camera's viewport never goes beyond the edge of the map
        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, Constants.CAMPUS_MAX_X - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, Constants.CAMPUS_MAX_Y - effectiveViewportHeight / 2f);
		
	}

    /**
     * Called whenever the game window is resized
     * @param width
     * @param height
     * @param campusMap
     */
    public static void gameScreenResize(int width, int height, CampusMap campusMap) {
        campusMap.getStage().getViewport().update(width, height);
        campusMap.getCamera().viewportWidth = width;
        campusMap.getCamera().viewportHeight = height;
        campusMap.getCamera().update();
    }

    public static boolean gameScreenTouchDown(int screenX, int screenY, int pointer, int button, CampusMap campusMap) {
        campusMap.setLastTouch(screenX, screenY);
        return campusMap.getStage().touchDown(screenX, screenY, pointer, button);
    }

    public static boolean gameScreenTouchUp(int screenX, int screenY, int pointer, int button, CampusMap campusMap) {
        return campusMap.getStage().touchUp(screenX, screenY, pointer, button);
    }

    /**
     * Called whenever the position of the user's mouse changes slightly. Moves map if the middle mouse button is pressed
     * @param screenX
     * @param screenY
     * @param pointer
     * @param campusMap
     * @return
     */
    public static boolean gameScreenDrag(int screenX, int screenY, int pointer, CampusMap campusMap) {
        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE))  {
            clampedTranslate((campusMap.getLastTouchX()-screenX), -(campusMap.getLastTouchY()-screenY), campusMap.getCamera());
            campusMap.setLastTouch(screenX, screenY);
        }
        
        // Input handler would ordinarily be set to the stage. Now we've finished, pass input events to stage.
        return campusMap.getStage().touchDragged(screenX, screenY, pointer);
    }

    /**
     * Used to handle zooming in and out in response to mouse wheel scroll
     * @param scrollAmount Magnitude of scroll on y-Axis
     * @param campusMap
     * @return
     */
    public static boolean gameScreenScroll(float scrollAmount, CampusMap campusMap) {
        // We find the in-game coordinates of the cursor initially
        Vector3 cursorPosMeters = campusMap.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        campusMap.getCamera().zoom += scrollAmount/10;

        // Minimum zoom is the entire viewport filling the screen and the maximum is an arbitrary 0.1 (On a 1080p monitor this would be ~98m)
		campusMap.getCamera().zoom = MathUtils.clamp(campusMap.getCamera().zoom, 0.1f, Math.min(Constants.CAMPUS_MAX_X/campusMap.getCamera().viewportWidth,
                                                                                                    Constants.CAMPUS_MAX_Y/campusMap.getCamera().viewportHeight));

        campusMap.getCamera().update();

        // Get the in-game coordinates of the cursor now the zoom has been executed
        Vector3 newCursorPosMeters = campusMap.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        
        // To make zoom natural, whatever is directly under the cursor should stay in the same place
        clampedTranslate(cursorPosMeters.x-newCursorPosMeters.x,cursorPosMeters.y-newCursorPosMeters.y, campusMap.getCamera());
        campusMap.getCamera().update();
		return true;
    }
}
