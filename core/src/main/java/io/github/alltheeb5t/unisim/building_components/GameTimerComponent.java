package io.github.alltheeb5t.unisim.building_components;

/**
 * Creates a game timer component which stores the amount of time passed in milliseconds.
 */
public class GameTimerComponent {

    float elapsedTime;

    public GameTimerComponent() {
        elapsedTime = 0;
    }

    public float getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(float elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
