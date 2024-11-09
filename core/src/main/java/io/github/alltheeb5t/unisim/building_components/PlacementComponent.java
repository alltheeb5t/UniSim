package io.github.alltheeb5t.unisim.building_components;

/**
 * Stores an X and Y coordinate representing the position where an element was last placed.
 */
public class PlacementComponent {
    private float positionX = -1;
    private float positionY = -1;

    public void setPosition(float x, float y) {
        positionX = x;
        positionY = y;
    }

    /**
     * Get Stored X Pos
     * @return X Pos, -1 if position never set
     */
    public float getXPos() {
        return positionX;
    }

    /**
     * Get Stored Y Pos
     * @return Y Pos, -1 if position never set
     */
    public float getYPos() {
        return positionY;
    }
}
