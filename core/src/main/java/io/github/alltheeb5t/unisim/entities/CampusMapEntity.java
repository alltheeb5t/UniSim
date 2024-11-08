package io.github.alltheeb5t.unisim.entities;

import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;
import io.github.alltheeb5t.unisim.building_components.MapBoundaryComponent;
import io.github.alltheeb5t.unisim.building_components.SatisfactionComponent;

/**
 * The Campus Map Entity stores components that relate to the spatial location of structures on the map.
 * CampusMapSystem, for example uses this component to determine whether a space is available.
 */
public class CampusMapEntity {
    private List<BoundingBoxComponent> existingStructureBounds = new LinkedList<>();
    private List<SatisfactionComponent> satisfactionComponents = new LinkedList<>();
    private List<Image> imagesObjects = new LinkedList<>();
    private static MapBoundaryComponent mapBoundaryComponent = new MapBoundaryComponent(2000, 900);
    
    public void addStructureBoundingBox(BoundingBoxComponent boundingBox) {
        existingStructureBounds.add(boundingBox);
    }

    public void addSatisfactionComponent(SatisfactionComponent satisfactionComponent) {
        satisfactionComponents.add(satisfactionComponent);
    }

    /**
     * Every building and obstructions's bounding box. They are anonymized from the images
     * they relate to
     * @return
     */
    public List<BoundingBoxComponent> getStructureBounds() {
        return existingStructureBounds;
    }

    /**
     * Every building's individual satisfaction component is included in this list.
     * The SatisfactionComponents are anonymized from their buildings though now.
     * @return
     */
    public List<SatisfactionComponent> getSatisfactionComponents() {
        return satisfactionComponents;
    }

    /**
     * Image is a standard Scene2D Image. Drag events are handled on images
     * @return
     */
    public List<Image> getImageObjects() {
        return imagesObjects;
    }

    public static MapBoundaryComponent getMapBoundaryComponent() {
        return mapBoundaryComponent;
    }
}
