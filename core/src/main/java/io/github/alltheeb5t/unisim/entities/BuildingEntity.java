package io.github.alltheeb5t.unisim.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;
import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;
import io.github.alltheeb5t.unisim.building_components.StructureNameComponent;
import io.github.alltheeb5t.unisim.building_components.StructureTypeComponent;

/**
 * A specific type of map structure which represents different types of building.
 * The drag and actual rendering is handled entirely by the imageComponent while boundingBox is used to detect collisions.
 */
public class BuildingEntity extends MapStructureEntity {
    private SatisfactionComponent satisfactionComponent;
    private StructureTypeComponent structureTypeComponent;
    private StructureNameComponent structureNameComponent;

    public BuildingEntity(BoundingBoxComponent boundingBoxComponent, Image imageComponent, SatisfactionComponent satisfactionComponent, StructureTypeComponent structureTypeComponent, StructureNameComponent structureNameComponent) {
        this.boundingBoxComponent = boundingBoxComponent;
        this.imageComponent = imageComponent;
        this.satisfactionComponent = satisfactionComponent;
        this.structureTypeComponent = structureTypeComponent;
        this.structureNameComponent = structureNameComponent;
    }

    public SatisfactionComponent getSatisfactionComponent() {
        return satisfactionComponent;
    }

    public StructureTypeComponent getStructureTypeComponent() {
        return structureTypeComponent;
    }

    public StructureNameComponent getStructureNameComponent() {
        return structureNameComponent;
    }
}
