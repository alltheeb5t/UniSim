package io.github.alltheeb5t.unisim.building_components;

/**
 * Stores values that define the boundaries of the map
 */
public class MapBoundaryComponent {
    private int campusMaxY; // Measured in meters
    private int campusMaxX; // Measured in meters

    public MapBoundaryComponent(int maxX, int maxY) {
        this.campusMaxX = maxX;
        this.campusMaxY = maxY;
    }

    public int getMaxX() {
        return campusMaxX;
    }

    public int getMaxY() {
        return campusMaxY;
    }
}
