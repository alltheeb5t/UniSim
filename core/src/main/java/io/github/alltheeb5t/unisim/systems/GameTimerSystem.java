package io.github.alltheeb5t.unisim.systems;

import io.github.alltheeb5t.unisim.building_components.GameTimerComponent;
import io.github.alltheeb5t.unisim.entities.GameTimerEntity;

/**
 * Controls the game timer in a game timer entity.
 */
public class GameTimerSystem {
    
    GameTimerEntity gameTimer;
    Boolean paused = false;

    public GameTimerSystem() {
        gameTimer = new GameTimerEntity();
    }

    /**
     * Adds to the elapsed time stored in the game timer every time render is called by the amount of time passed
     * since render was last called. It does not add if the game has been paused.
     * @param addedTime
     */
    public void tick(float addedTime) {
        if (!paused) {
            GameTimerComponent timePassed = gameTimer.getGameTimerRecord();
            timePassed.setElapsedTime(timePassed.getElapsedTime() + addedTime);
        }
    }

    /**
     * Creates and returns a string output of the elapsed time in the format mm:ss.
     * @return
     */
    public String timeDisplay() {
        float secondsPassed = gameTimer.getGameTimerRecord().getElapsedTime();
        int minutes = (int)secondsPassed / 60;
        int seconds = (int)secondsPassed - (minutes * 60);
        String output = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        return output;
    }

    /**
     * Changes the value of the variable paused. Therefore stopping or resuming the timer.
     */
    public void toggleTimer() {
        paused = !paused;
    }
}
