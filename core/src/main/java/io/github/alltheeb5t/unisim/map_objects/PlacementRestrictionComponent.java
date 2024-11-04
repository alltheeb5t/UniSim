package io.github.alltheeb5t.unisim.map_objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Represents any object that is to be drawn on the map.
 */
public class PlacementRestrictionComponent {
    protected float width, height;
    protected Body body;
    protected Image imageObject;

    /**
     * Constructor allowing specification of arbitrary width and height parameters.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param imageObject
     * @param body
     */
    public PlacementRestrictionComponent(float x, float y, float width, float height, Image imageObject, Body body) {
        this.body = body;
        this.imageObject = imageObject;
        this.width = width;
        this.height = height;
    }

    public Body getBody() {
        return body;
    }

    public Image getImageObject() {
        return imageObject;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
