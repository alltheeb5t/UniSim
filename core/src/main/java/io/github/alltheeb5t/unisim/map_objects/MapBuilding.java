package io.github.alltheeb5t.unisim.map_objects;

/**
 * Represents a special type of map obstruction which has additional properties like cost etc.
 */
public class MapBuilding {
    MapObstruction myObstructionFootprint;

    public MapBuilding(MapObstruction myObstructionFootprint) {
        this.myObstructionFootprint = myObstructionFootprint;
    }

    public MapObstruction geMapObstruction() {
        return myObstructionFootprint;
    }
}
