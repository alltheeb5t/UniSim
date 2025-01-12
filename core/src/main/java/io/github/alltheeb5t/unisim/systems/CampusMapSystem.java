package io.github.alltheeb5t.unisim.systems;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;
import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;
import io.github.alltheeb5t.unisim.entities.BuildingEntity;
import io.github.alltheeb5t.unisim.entities.CampusMapEntity;
import io.github.alltheeb5t.unisim.entities.MapObstacleEntity;

/**
 * Stores information relevant to spatial position of structures on the map
 */
public class CampusMapSystem {
    /**
     * Stores building bounding box and satisfactionComponent within the CampusMapEntity.
     * This method does not draw anything on the screen
     * @param campusMapEntity
     * @param boundingBoxComponent
     * @param imageComponent
     * @param satisfactionComponent
     */
    public static void addBuildingToMap(CampusMapEntity campusMapEntity, BoundingBoxComponent boundingBoxComponent, Image imageComponent, SatisfactionComponent satisfactionComponent) {
        campusMapEntity.addSatisfactionComponent(satisfactionComponent);
        campusMapEntity.addStructureBoundingBox(boundingBoxComponent);
    }

    /**
     * Adds the bounding box of all obstacles to the campus map for the purpose of collision detection
     * @param campusMapEntity
     * @param allObstacles
     */
    public static void addAllObstaclesToMap(CampusMapEntity campusMapEntity, List<MapObstacleEntity> allObstacles) {
        Iterator<MapObstacleEntity> obstacleIterator = allObstacles.iterator();

        while (obstacleIterator.hasNext()) {
            campusMapEntity.addStructureBoundingBox(obstacleIterator.next().getBoundingBoxComponent());
        }
        
    }
    
    /**
     * Examine all existing structures on Map and check if any collide with the building in motion
     * @param campusMapEntity
     * @param buildingInMotion
     * @return
     */
    public static boolean isPlacementAllowed(CampusMapEntity campusMapEntity, BuildingEntity buildingInMotion) {
        Iterator<BoundingBoxComponent> existingRestrictions = campusMapEntity.getStructureBounds().iterator();

        while (existingRestrictions.hasNext()) {
            BoundingBoxComponent boundingBox = existingRestrictions.next();
            if (boundingBox != buildingInMotion.getBoundingBoxComponent()) {  // Otherwise would identify a collision with itself
                if (boundingBox.getRectangularBoundingBox().overlaps(buildingInMotion.getBoundingBoxComponent().getRectangularBoundingBox())) {
                    return false;
                }
            }
        }

        return true;
    }
}
