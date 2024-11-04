package io.github.alltheeb5t.unisim.factories;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.map_objects.PlacementRestrictionComponent;
import io.github.alltheeb5t.unisim.systems.BuildingSystem;

public class PlacementRestrictionFactory {
    /**
     * Generate a generic map obstruction. Generic obstructions have collision boxes but cannot be dragged.
     * @param x
     * @param y
     * @param width
     * @param height
     * @param world
     * @param texture
     * @return
     */
    public static PlacementRestrictionComponent makePlacementRestrictionComponent(float x, float y, float width, float height, World world, Texture texture) {
        Body newObjectBody = BodyFactory.createBody(x, y, width, height, false, world);
        
        Image newObjectImageObject = new Image(texture);
        newObjectImageObject.setPosition(x-width/2, y-height/2);
        newObjectImageObject.setSize(width, height);
        
        PlacementRestrictionComponent newMapObstruction = new PlacementRestrictionComponent(x, y, width, height, newObjectImageObject, newObjectBody);

        BuildingSystem.syncBodyPosition(newMapObstruction);

        return newMapObstruction;

    }

    /**
     * Makes a new map Obstruction where the height is determined based on aspect ratio of input image
     * @param x
     * @param y
     * @param width
     * @param world
     * @param texture
     * @return
     */
    public static PlacementRestrictionComponent makePlacementRestrictionComponent(float x, float y, float width, World world, Texture texture) {
        return makePlacementRestrictionComponent(x, y, width, width/(1.0f*texture.getWidth()/texture.getHeight()), world, texture);
    }
}
