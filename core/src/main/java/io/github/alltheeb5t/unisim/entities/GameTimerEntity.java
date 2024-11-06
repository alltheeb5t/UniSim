package io.github.alltheeb5t.unisim.entities;

import io.github.alltheeb5t.unisim.building_components.GameTimerComponent;

/**
 * Creates a game timer entity which contains a game timer component.
 */
public class GameTimerEntity {

    GameTimerComponent gameTimerRecord;

    public GameTimerEntity() {
        gameTimerRecord = new GameTimerComponent();
    }

    public GameTimerComponent getGameTimerRecord() {
        return gameTimerRecord;
    }
}
