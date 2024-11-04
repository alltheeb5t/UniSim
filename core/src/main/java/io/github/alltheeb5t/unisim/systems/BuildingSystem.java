package io.github.alltheeb5t.unisim.systems;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.map_objects.MapBuilding;
import io.github.alltheeb5t.unisim.map_objects.PlacementRestrictionComponent;

public class BuildingSystem {
    /**
     * Called repeatedly when building being dragged. Ensures the box2D Body lines up with image position.
     */
    public static void syncBodyPosition(PlacementRestrictionComponent mapObstruction) {
        // Static objects should not have any drag functionality
        if (mapObstruction.getBody().getType() == BodyType.DynamicBody) {
            float x = mapObstruction.getImageObject().getX();
            float y = mapObstruction.getImageObject().getY();
            mapObstruction.getBody().setTransform(x+mapObstruction.getWidth()/2, y+mapObstruction.getHeight()/2, 0);
            System.out.println("Debug X: "+x+" Y: "+y);
        }
    }

    /**
     * Return the total number of a given building type.
     * @param type
     * @param allBuildings
     * @return Int number of buildings
     */
    public static int getBuildingCount(StructureTypeComponent type, List<MapBuilding> allBuildings) {
        int counter = 0;

        Iterator<MapBuilding> buildingIterator = allBuildings.iterator();

        while (buildingIterator.hasNext()) {
            if (buildingIterator.next().getStructureTypeComponent() == type) {
                counter ++;
            }
        }

        return counter;
    }

}
