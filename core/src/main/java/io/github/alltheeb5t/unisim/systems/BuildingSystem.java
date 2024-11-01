package io.github.alltheeb5t.unisim.systems;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import io.github.alltheeb5t.unisim.map_objects.MapObstruction;

public class BuildingSystem {
    /**
     * Called repeatedly when building being dragged. Ensures the box2D Body lines up with image position.
     */
    public static void syncBodyPosition(MapObstruction mapObstruction) {
        // Static objects should not have any drag functionality
        if (mapObstruction.getBody().getType() == BodyType.DynamicBody) {
            float x = mapObstruction.getImageObject().getX();
            float y = mapObstruction.getImageObject().getY();
            mapObstruction.getBody().setTransform(x+mapObstruction.getWidth()/2, y+mapObstruction.getHeight()/2, 0);
            System.out.println("Debug X: "+x+" Y: "+y);
        }
    }

}
