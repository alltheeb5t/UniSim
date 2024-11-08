package io.github.alltheeb5t.unisim.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

import io.github.alltheeb5t.unisim.entities.BuildingEntity;
import io.github.alltheeb5t.unisim.entities.CampusMapEntity;
import io.github.alltheeb5t.unisim.entities.LibGdxRenderingEntity;

public class MapInputSystem {

    // ─── Building Movement ───────────────────────────────────────────────

    public static void registerDraggableObstruction(LibGdxRenderingEntity libGdxRenderingEntity, BuildingEntity buildingEntity, CampusMapEntity campusMapEntity) {
        // Drag and drop listener for specified building
        libGdxRenderingEntity.getDragAndDrop().addSource(new DragAndDrop.Source(buildingEntity.getImageComponent()) {
            @Override
			public DragAndDrop.Payload dragStart(InputEvent inputEvent, float v, float v1, int i) {
                DragAndDrop.Payload payload = new DragAndDrop.Payload();
				payload.setDragActor(getActor());
                libGdxRenderingEntity.getStage().addActor(buildingEntity.getImageComponent());
                buildingEntity.getImageComponent().toFront(); // Ensures that entity being dragged around is always rendered above others
                libGdxRenderingEntity.getDragAndDrop().setDragActorPosition(getActor().getWidth() / 2, -getActor().getHeight() / 2); // Move image so the cursor is in the centre of the object during dragging

				return payload;
			}

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                super.drag(event, x, y, pointer);
                BuildingSystem.syncBoundingBoxPosition(buildingEntity);

                // Turn image red if a collision is detected
                if (!CampusMapSystem.isPlacementAllowed(campusMapEntity, buildingEntity)) {
                    buildingEntity.getImageComponent().setColor(255, 0, 0, 255);
                } else {
                    buildingEntity.getImageComponent().setColor(255, 255, 255, 255);
                }
            }

			@Override
			public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
				buildingEntity.getImageComponent().setColor(255, 255, 255, 255); // Clear red colouring

                if (CampusMapSystem.isPlacementAllowed(campusMapEntity, buildingEntity)) {
                    BuildingSystem.updatePlacementPosition(buildingEntity);
                } else {
                    BuildingSystem.returnToPlacedPosition(buildingEntity);
                }
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
        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, CampusMapEntity.getMapBoundaryComponent().getMaxX() - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, CampusMapEntity.getMapBoundaryComponent().getMaxY() - effectiveViewportHeight / 2f);
		
	}

    /**
     * Called whenever the game window is resized
     * @param width
     * @param height
     * @param libGdxRenderingEntity
     */
    public static void gameScreenResize(int width, int height, LibGdxRenderingEntity libGdxRenderingEntity) {
        libGdxRenderingEntity.getStage().getViewport().update(width, height);
        libGdxRenderingEntity.getCamera().viewportWidth = width;
        libGdxRenderingEntity.getCamera().viewportHeight = height;
        libGdxRenderingEntity.getCamera().update();
    }

    public static boolean gameScreenTouchDown(int screenX, int screenY, int pointer, int button, LibGdxRenderingEntity libGdxRenderingEntity) {
        libGdxRenderingEntity.setLastTouch(screenX, screenY);
        return libGdxRenderingEntity.getStage().touchDown(screenX, screenY, pointer, button);
    }

    public static boolean gameScreenTouchUp(int screenX, int screenY, int pointer, int button, LibGdxRenderingEntity libGdxRenderingEntity) {
        return libGdxRenderingEntity.getStage().touchUp(screenX, screenY, pointer, button);
    }

    /**
     * Called whenever the position of the user's mouse changes slightly. Moves map if the middle mouse button is pressed
     * @param screenX
     * @param screenY
     * @param pointer
     * @param libGdxRenderingEntity
     * @return
     */
    public static boolean gameScreenDrag(int screenX, int screenY, int pointer, LibGdxRenderingEntity libGdxRenderingEntity) {
        if (Gdx.input.isButtonPressed(Input.Buttons.MIDDLE))  {
            clampedTranslate((libGdxRenderingEntity.getLastTouchX()-screenX), -(libGdxRenderingEntity.getLastTouchY()-screenY), libGdxRenderingEntity.getCamera());
            libGdxRenderingEntity.setLastTouch(screenX, screenY);
        }
        
        // Input handler would ordinarily be set to the stage. Now we've finished, pass input events to stage.
        return libGdxRenderingEntity.getStage().touchDragged(screenX, screenY, pointer);
    }

    /**
     * Used to handle zooming in and out in response to mouse wheel scroll
     * @param scrollAmount Magnitude of scroll on y-Axis
     * @param libGdxRenderingEntity
     * @return
     */
    public static boolean gameScreenScroll(float scrollAmount, LibGdxRenderingEntity libGdxRenderingEntity) {
        // We find the in-game coordinates of the cursor initially
        Vector3 cursorPosMeters = libGdxRenderingEntity.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        libGdxRenderingEntity.getCamera().zoom += scrollAmount/10;

        // Minimum zoom is the entire viewport filling the screen and the maximum is an arbitrary 0.1 (On a 1080p monitor this would be ~98m)
		libGdxRenderingEntity.getCamera().zoom = MathUtils.clamp(libGdxRenderingEntity.getCamera().zoom, 0.1f, Math.min(CampusMapEntity.getMapBoundaryComponent().getMaxX()/libGdxRenderingEntity.getCamera().viewportWidth,
        CampusMapEntity.getMapBoundaryComponent().getMaxY()/libGdxRenderingEntity.getCamera().viewportHeight));

        libGdxRenderingEntity.getCamera().update();

        // Get the in-game coordinates of the cursor now the zoom has been executed
        Vector3 newCursorPosMeters = libGdxRenderingEntity.getCamera().unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        
        // To make zoom natural, whatever is directly under the cursor should stay in the same place
        clampedTranslate(cursorPosMeters.x-newCursorPosMeters.x,cursorPosMeters.y-newCursorPosMeters.y, libGdxRenderingEntity.getCamera());
        libGdxRenderingEntity.getCamera().update();
		return true;
    }
}
