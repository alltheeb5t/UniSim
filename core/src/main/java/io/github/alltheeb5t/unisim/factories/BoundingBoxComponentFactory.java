package io.github.alltheeb5t.unisim.factories;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;

/**
 * Used to create new bounding boxes.
 * At the moment, all bounding boxes are rectangles so only a width, height, x and y is needed.
 */
public class BoundingBoxComponentFactory {
    /**
     * Generates a bounding box using the dimensions from a previously created image object.
     * This means that an image can be created and a bounding box automatically will match its dimensions.
     * @param imageComponent
     * @return
     */
    public static BoundingBoxComponent makeBoundingBoxComponent(Image imageComponent) {
        return makeBoundingBoxComponent(imageComponent.getX(),
                                        imageComponent.getY(),
                                        imageComponent.getWidth(),
                                        imageComponent.getHeight());
    }

    public static BoundingBoxComponent makeBoundingBoxComponent(float x, float y, float width, float height) {
        BoundingBoxComponent newComponent = new BoundingBoxComponent();
        newComponent.getRectangularBoundingBox().set(x, y, width, height);
        return newComponent;
    }
}
