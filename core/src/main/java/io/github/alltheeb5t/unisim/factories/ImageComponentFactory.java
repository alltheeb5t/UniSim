package io.github.alltheeb5t.unisim.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * ImageComponent (AKA Image) is just a naked Scene2D Image object. The ImageComponentFactory instantiates an image and sets dimensions correctly.
 */
public class ImageComponentFactory {
    public static Image makeImageComponent(float x, float y, float width, float height, Texture texture) {
        Image newObjectImageObject = new Image(texture);
        newObjectImageObject.setPosition(x-width/2, y-height/2);
        newObjectImageObject.setSize(width, height);

        return newObjectImageObject;
    }

    /**
     * Offer the functionality to create an ImageComponent whose height is calculated automatically so the aspect ratio of the image is correct
     */
    public static Image makeImageComponent(float x, float y, float width, Texture texture) {
        return makeImageComponent(x, y, width, width/(1.0f*texture.getWidth()/texture.getHeight()), texture);
    }
}
