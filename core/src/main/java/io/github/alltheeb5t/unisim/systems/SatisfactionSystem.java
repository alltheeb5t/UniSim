package io.github.alltheeb5t.unisim.systems;

import java.util.Iterator;
import java.util.List;

import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;
import io.github.alltheeb5t.unisim.map_objects.MapBuilding;

public class SatisfactionSystem {
    public static float getAverageSatisfaction(List<SatisfactionComponent> satisfaction_objects) {
        float total = 0;
        for (int i = 0; i < satisfaction_objects.size(); i++) {
            total += satisfaction_objects.get(i).getLastCalculatedSatisfaction();
        }

        return total / satisfaction_objects.size();
    }

    public static float calculateIndividualBuildingSatisfaction(MapBuilding building, List<MapBuilding> allBuildings) {
        return 0;
    }

    public static void recalculateBuildingSatisfaction(List<MapBuilding> allBuildings) {
        Iterator<MapBuilding> building = allBuildings.iterator();

        while (building.hasNext()) {
            MapBuilding currentBuilding = building.next();
            currentBuilding.getSatisfactionComponent().setLastCalculatedSatisfaction(calculateIndividualBuildingSatisfaction(currentBuilding, allBuildings));
        }
    }
}
