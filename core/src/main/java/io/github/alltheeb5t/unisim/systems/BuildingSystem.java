package io.github.alltheeb5t.unisim.systems;

import java.util.Iterator;
import java.util.List;

import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.entities.BuildingEntity;

public class BuildingSystem {
    /**
     * Called repeatedly when building being dragged. Ensures the BoundingBoxComponent matches the image position.
     */
    public static void syncBoundingBoxPosition(BuildingEntity buildingEntity) {
        // Static objects should not have any drag functionality
        float x = buildingEntity.getImageComponent().getX();
        float y = buildingEntity.getImageComponent().getY();
        buildingEntity.getBoundingBoxComponent().getRectangularBoundingBox().setPosition(x, y);
        System.out.println("Debug X: "+x+" Y: "+y);
    }

    /**
     * Return the total number of a given building type.
     * @param type
     * @param allBuildings
     * @return Int number of buildings
     */
    public static int getBuildingCount(StructureTypeComponent type, List<BuildingEntity> allBuildings) {
        int counter = 0;

        Iterator<BuildingEntity> buildingIterator = allBuildings.iterator();

        while (buildingIterator.hasNext()) {
            if (buildingIterator.next().getStructureTypeComponent() == type) {
                counter ++;
            }
        }

        return counter;
    }

}
