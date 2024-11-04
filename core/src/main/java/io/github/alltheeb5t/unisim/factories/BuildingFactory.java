package io.github.alltheeb5t.unisim.factories;

import com.badlogic.gdx.graphics.Texture;

import io.github.alltheeb5t.unisim.CampusMap;
import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;
import io.github.alltheeb5t.unisim.building_components.StructureNameComponent;
import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;
import io.github.alltheeb5t.unisim.map_objects.MapBuilding;
import io.github.alltheeb5t.unisim.map_objects.PlacementRestrictionComponent;
import io.github.alltheeb5t.unisim.systems.MapInputSystem;

public class BuildingFactory {
        /**
     * Generates a new building object. Buildings still have an Obstruction object but they allow this object to be dragged around
     * @param mapObstructionFootprint
     * @param campusMap
     * @return
     */
    public static MapBuilding makeMapBuilding(float x, float y, StructureTypeComponent type, SatisfactionComponent satisfaction, CampusMap campusMap) {
        
        StructureNameComponent newBuildingName;
        PlacementRestrictionComponent mapObstructionFootprint;

        switch (type) {
            case CATERING:
                newBuildingName = new StructureNameComponent("Piazza");
                mapObstructionFootprint = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 120, campusMap.getWorld(), new Texture("piazza.png"));
                break;
            case ACCOMMODATION:
                newBuildingName = new StructureNameComponent("John West Taylor Court");
                mapObstructionFootprint = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 60, campusMap.getWorld(), new Texture("john_west_taylor_court.png"));
                break;
            default:
                newBuildingName = new StructureNameComponent("Piazza");
                mapObstructionFootprint = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 120, campusMap.getWorld(), new Texture("piazza.png"));
        }
        
        campusMap.getStage().addActor(mapObstructionFootprint.getImageObject());
        MapInputSystem.registerDraggableObstruction(campusMap.getDragAndDrop(), campusMap.getStage(), mapObstructionFootprint);

        return new MapBuilding(mapObstructionFootprint, newBuildingName, type, satisfaction);
    }
}
