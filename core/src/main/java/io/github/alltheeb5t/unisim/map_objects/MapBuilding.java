package io.github.alltheeb5t.unisim.map_objects;

import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;
import io.github.alltheeb5t.unisim.building_components.StructureNameComponent;
import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;

/**
 * Represents a special type of map obstruction which has additional properties like cost etc.
 */
public class MapBuilding {
    PlacementRestrictionComponent myObstructionFootprint;
    StructureNameComponent name;
    StructureTypeComponent type;
    SatisfactionComponent satisfaction;

    public MapBuilding(PlacementRestrictionComponent myObstructionFootprint, StructureNameComponent name, StructureTypeComponent type, SatisfactionComponent satisfaction) {
        this.myObstructionFootprint = myObstructionFootprint;
        this.name = name;
        this.type = type;
        this.satisfaction = satisfaction;
    }

    // TODO Change the name of this to getPlacementRestriction
    public PlacementRestrictionComponent geMapObstruction() {
        return myObstructionFootprint;
    }

    public StructureTypeComponent getStructureTypeComponent() {
        return type;
    }

    public SatisfactionComponent getSatisfactionComponent() {
        return satisfaction;
    }
}
