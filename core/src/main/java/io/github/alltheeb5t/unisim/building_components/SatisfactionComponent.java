package io.github.alltheeb5t.unisim.building_components;

public class SatisfactionComponent {
    private int lastCalculatedSatisfaction;

    public SatisfactionComponent() {
        lastCalculatedSatisfaction = 0;
    }

    public int getLastCalculatedSatisfaction() {
        return lastCalculatedSatisfaction;
    }

    public int setLastCalculatedSatisfaction(int newValue) {
        lastCalculatedSatisfaction = newValue;
        return lastCalculatedSatisfaction;
    }
}
