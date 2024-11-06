package io.github.alltheeb5t.unisim.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;

/**
 * This class is used as a parent for both Buildings and Obstacles. Both have an image and bounding box for collision
 */
public abstract class MapStructureEntity {
    protected Image imageComponent;
    protected BoundingBoxComponent boundingBoxComponent;

    /**
     * Get the Scene2D Image object associated with this structure
     * @return Scene2D Image object
     */
    public Image getImageComponent() {
        return imageComponent;
    }

    /**
     * Return a bounding box.
     * The bounding box stores size and position as a LibGDX rectangle object for easy collision detection
     * @return
     */
    public BoundingBoxComponent getBoundingBoxComponent() {
        return boundingBoxComponent;
    }
}
