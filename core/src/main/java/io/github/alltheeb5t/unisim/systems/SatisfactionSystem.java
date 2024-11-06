package io.github.alltheeb5t.unisim.systems;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;
import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.entities.BuildingEntity;

/**
 * The SatisfactionSystem provides the methods that calculates the satisfaction value for each building.
 * Overall satisfaction is an average of all building's satisfaction.
 */
public class SatisfactionSystem {
    /**
     * Calculated satisfaction values are stored within the Satisfaction Component.
     * No calculation of individual building satisfaction is performed when this method
     * is called. This is invoked with `recalculateBuildingSatisfaction()`.
     * @param satisfaction_objects
     * @return A single average satisfaction value
     */
    public static float getAverageSatisfaction(List<SatisfactionComponent> satisfaction_objects) {
        float total = 0;
        for (int i = 0; i < satisfaction_objects.size(); i++) {
            total += satisfaction_objects.get(i).getLastCalculatedSatisfaction();
        }

        return total / satisfaction_objects.size();
    }

    /**
     * Returns the distance between the target building and the nearest building of specified type.
     * @param type: StructureTypeComponent
     * @param targetBuilding: BuildingEntity
     * @param allBuildings
     * @return float - Distance value
     */
    private static float getNearestBuildingOfType(StructureTypeComponent type, BuildingEntity targetBuilding, List<BuildingEntity> allBuildings) {
        // Increase efficiency slightly by not going through Pythagoras calculation just to get 0;
        if (targetBuilding.getStructureTypeComponent() == type) {
            return 0;
        }

        Iterator<BuildingEntity> building = allBuildings.iterator();

        float currentClosest = Float.MAX_VALUE;

        while (building.hasNext()) {
            BuildingEntity currentBuilding = building.next();
            if (currentBuilding.getStructureTypeComponent() == type) {
                // Calculate shortest distance using pythagoras' theorem. Split into variables to make less unwieldy.
                float dX = (targetBuilding.getImageComponent().getX() - currentBuilding.getImageComponent().getX());
                float dY = (targetBuilding.getImageComponent().getY() - currentBuilding.getImageComponent().getY());
                float newDistance = (float)(Math.sqrt(Math.pow(dX, 2)+Math.pow(dY, 2)));

                currentClosest = Math.min(currentClosest, newDistance);
            }
        }

        return currentClosest;
    }


    // ─── Calculation Of Satisfactions For Different Building Types ───────

    /**
     * Translate the distances to each building type into a single satisfaction score.
     * This method calculates the score based on the specific requirements of an accommodation building.
     * 
     * Current implementation is as follows:
     *  If a building is closer than a given distance, satisfaction is 100%
     *  Otherwise it is inversely proportional to distance such that the maximum value is 100%.
     * @param distanceMap Mapping between StructureTypeComponent and a Float representing distance
     * @return Single satisfaction value
     */
    private static float getAccommodationBuildingSatisfaction(Map<StructureTypeComponent, Float> distanceMap) {
        // Students in accommodation are more satisfied if they're closer to catering
        float foodSatisfaction;
        if (distanceMap.get(StructureTypeComponent.CATERING) < 100) {
            foodSatisfaction = 100;
        } else {
            foodSatisfaction = 10000 / distanceMap.get(StructureTypeComponent.CATERING);
        }

        // Students are more satisfied if they don't have to walk far to lectures.
        float educationSatisfaction;
        if (distanceMap.get(StructureTypeComponent.STUDY) < 50) {
            educationSatisfaction = 100;
        } else {
            educationSatisfaction = 5000 / distanceMap.get(StructureTypeComponent.STUDY);
        }

        // Students are more satisfied if they have a place to have fun.
        // I assume they're willing to walk further to access this (800m before satisfaction suffers)
        float entertainmentSatisfaction;
        if (distanceMap.get(StructureTypeComponent.ENTERTAINMENT) < 800) {
            entertainmentSatisfaction = 100;
        } else {
            entertainmentSatisfaction = 80000 / distanceMap.get(StructureTypeComponent.ENTERTAINMENT);
        }

        System.out.println("DEBUG: foodSatisfaction "+foodSatisfaction+", educationSatisfaction "+educationSatisfaction+", entertainmentSatisfaction "+entertainmentSatisfaction);

        // Building satisfaction is a simple average of the above metrics
        return (foodSatisfaction+educationSatisfaction+entertainmentSatisfaction)/3;
    }

    /**
     * For the specified building, calculate the distance to all other types of buildings and then calculate satisfaction score according to building type.
     * For assessment 1, all buildings are treated as if they are accommodation for simplicity.
     * @param building Target building for which satisfaction score is to be calculated
     * @param allBuildings List of all buildings to find distance
     * @return
     */
    private static float calculateIndividualBuildingSatisfaction(BuildingEntity building, List<BuildingEntity> allBuildings) {
        Map<StructureTypeComponent, Float> distanceMap = Map.of(
            StructureTypeComponent.ACCOMMODATION, getNearestBuildingOfType(StructureTypeComponent.ACCOMMODATION, building, allBuildings),
            StructureTypeComponent.CATERING, getNearestBuildingOfType(StructureTypeComponent.CATERING, building, allBuildings),
            StructureTypeComponent.STUDY, getNearestBuildingOfType(StructureTypeComponent.STUDY, building, allBuildings),
            StructureTypeComponent.ENTERTAINMENT, getNearestBuildingOfType(StructureTypeComponent.ENTERTAINMENT, building, allBuildings)
        );

        // For the purpose of Assessment 1, I'm treating all buildings as accommodation.
        return getAccommodationBuildingSatisfaction(distanceMap);
    }

    /**
     * Recalculates the satisfaction score for all buildings on the map.
     * Calculated value is stored in a building's associated BuildingSatisfactionComponent.
     * @param allBuildings
     */
    public static void recalculateBuildingSatisfaction(List<BuildingEntity> allBuildings) {
        Iterator<BuildingEntity> building = allBuildings.iterator();

        while (building.hasNext()) {
            BuildingEntity currentBuilding = building.next();
            currentBuilding.getSatisfactionComponent().setLastCalculatedSatisfaction(calculateIndividualBuildingSatisfaction(currentBuilding, allBuildings));
        }
    }
}
