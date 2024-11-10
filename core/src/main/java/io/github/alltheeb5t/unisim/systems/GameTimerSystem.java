package io.github.alltheeb5t.unisim.systems;

import com.badlogic.gdx.Gdx;

import io.github.alltheeb5t.unisim.building_components.GameTimerComponent;
import io.github.alltheeb5t.unisim.entities.GameTimerEntity;

/**
 * Controls the game timer in a game timer entity.
 */
public class GameTimerSystem {

    /**
     * Adds to the elapsed time stored in the game timer every time render is called by the amount of time passed
     * since render was last called. It does not add if the game has been paused.
     * @param addedTime
     */
    public static void tick(float addedTime, GameTimerEntity gameTimer) {
        GameTimerComponent timePassed = gameTimer.getGameTimerRecord();

        if (!gameTimer.getPaused()) {            
            timePassed.setElapsedTime(timePassed.getElapsedTime() + addedTime);
        }

        if(timePassed.getElapsedTime() >= 300) {
            Gdx.app.exit();
            System.exit(-1);
        }
    }

    /**
     * Creates and returns a string output of the elapsed time in the format mm:ss.
     * @return
     */
    public static String timeDisplay(GameTimerEntity gameTimer) {
        float secondsPassed = gameTimer.getGameTimerRecord().getElapsedTime();
        int minutes = (int)secondsPassed / 60;
        int seconds = (int)secondsPassed - (minutes * 60);
        String output = String.format("%02d", minutes) + ":" + String.format("%02d", seconds);
        return output;
    }

    /**
     * Changes the value of the variable paused. Therefore stopping or resuming the timer.
     */
    public static void toggleTimer(GameTimerEntity gameTimer) {
        gameTimer.setPaused(!gameTimer.getPaused());
    }
}
