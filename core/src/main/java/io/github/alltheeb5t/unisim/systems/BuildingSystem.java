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
     * Called when a building has been successfully placed down after a drag event
     * @param buildingEntity
     */
    public static void updatePlacementPosition(BuildingEntity buildingEntity) {
        buildingEntity.getPlacementComponent().setPosition(
            buildingEntity.getImageComponent().getX(),
            buildingEntity.getImageComponent().getY());
    }

    /**
     * If a user stops dragging a building in an invalid location, we need to return it to its last valid location.
     * @param buildingEntity
     * @return True if the building was returned to an old location. False if the building had never previously been placed
     */
    public static boolean returnToPlacedPosition(BuildingEntity buildingEntity) {
        if (buildingEntity.getPlacementComponent().getXPos() == -1) {
            return false;
        }

        System.out.println("DEBUG: Returning building to: "+ buildingEntity.getPlacementComponent().getXPos()+ ", "+
        buildingEntity.getPlacementComponent().getYPos());

        buildingEntity.getImageComponent().setPosition(
            buildingEntity.getPlacementComponent().getXPos(),
            buildingEntity.getPlacementComponent().getYPos()
        );

        syncBoundingBoxPosition(buildingEntity);

        return true;
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
