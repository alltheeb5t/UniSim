package io.github.alltheeb5t.unisim.building_components;

public class SatisfactionComponent {
    private float lastCalculatedSatisfaction;

    public SatisfactionComponent() {
        lastCalculatedSatisfaction = 0;
    }

    public float getLastCalculatedSatisfaction() {
        return lastCalculatedSatisfaction;
    }

    public float setLastCalculatedSatisfaction(float newValue) {
        lastCalculatedSatisfaction = newValue;
        return lastCalculatedSatisfaction;
    }
}
