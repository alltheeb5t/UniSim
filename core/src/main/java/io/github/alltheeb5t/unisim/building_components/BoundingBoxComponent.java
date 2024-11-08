package io.github.alltheeb5t.unisim.building_components;

import com.badlogic.gdx.math.Rectangle;

/**
 * The bounding box component is associated with buildings and obstructions.
 * All bounding boxes at the current time are rectangles so the inbuilt rectangle overlaps method will be used to determine collisions
 */
public class BoundingBoxComponent {
    private Rectangle rectangularBoundingBox;

    /**
     * Create a simple rectangular boundingBox
     * @param x X coordinate of centre point
     * @param y Y coordinate of centre point
     * @param width
     * @param height
     */
    public BoundingBoxComponent() {
        rectangularBoundingBox = new Rectangle();
    }

    public Rectangle getRectangularBoundingBox() {
        return rectangularBoundingBox;
    }
}
