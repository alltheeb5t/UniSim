package io.github.alltheeb5t.unisim.map_objects;

import java.util.LinkedList;
import java.util.List;

/**
 * Creates a map obstacle component. Contains a list of placement restriction objects that make up the shape.
 */
public class MapObstacleComponent {
    List<PlacementRestrictionComponent> myObstructionFootprint = new LinkedList<>();

    public MapObstacleComponent(PlacementRestrictionComponent myObstructionFootprint) {
        this.myObstructionFootprint.add(myObstructionFootprint);
    }

    public MapObstacleComponent() {}

    public List<PlacementRestrictionComponent> getMapObstruction() {
        return myObstructionFootprint;
    }

    public void addAnotherSquare(PlacementRestrictionComponent newSquare) {
        myObstructionFootprint.add(newSquare);
    }
}
