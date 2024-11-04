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
                newBuildingName = new StructureNameComponent("Roger Kirk Centre");
                mapObstructionFootprint = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 120, campusMap.getWorld(), new Texture("roger_kirk.png"));
                break;
            case ACCOMMODATION:
                newBuildingName = new StructureNameComponent("John West Taylor Court");
                mapObstructionFootprint = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 60, campusMap.getWorld(), new Texture("john_west_taylor_court.png"));
                break;
            case ENTERTAINMENT:
                newBuildingName = new StructureNameComponent("York Barbican");
                mapObstructionFootprint = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 150, campusMap.getWorld(), new Texture("york_barbican.png"));
                break;
            case STUDY:
                newBuildingName = new StructureNameComponent("Law & Management Building");
                mapObstructionFootprint = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 100, campusMap.getWorld(), new Texture("law_and_management.png"));
                break;
            default:
                newBuildingName = new StructureNameComponent("Roger Kirk Centre");
                mapObstructionFootprint = PlacementRestrictionFactory.makePlacementRestrictionComponent(x, y, 120, campusMap.getWorld(), new Texture("roger_kirk.png"));
        }
        
        campusMap.getStage().addActor(mapObstructionFootprint.getImageObject());
        MapInputSystem.registerDraggableObstruction(campusMap.getDragAndDrop(), campusMap.getStage(), mapObstructionFootprint);

        return new MapBuilding(mapObstructionFootprint, newBuildingName, type, satisfaction);
    }
}
