package io.github.alltheeb5t.unisim.map_objects;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;
import io.github.alltheeb5t.unisim.entities.MapStructureEntity;

/**
 * Creates a map obstacle component. Contains a list of placement restriction objects that make up the shape.
 */
public class MapObstacleComponent extends MapStructureEntity {
    public MapObstacleComponent(BoundingBoxComponent boundingBoxComponent, Image imageComponent) {
        this.imageComponent = imageComponent;
        this.boundingBoxComponent = boundingBoxComponent;
    }
}
