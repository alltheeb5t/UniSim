package io.github.alltheeb5t.unisim.entities;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

import io.github.alltheeb5t.unisim.building_components.BoundingBoxComponent;

/**
 * Creates a map obstacle component. Contains a list of placement restriction objects that make up the shape.
 */
public class MapObstacleEntity extends MapStructureEntity {
    public MapObstacleEntity(BoundingBoxComponent boundingBoxComponent, Image imageComponent) {
        this.imageComponent = imageComponent;
        this.boundingBoxComponent = boundingBoxComponent;
    }
}
