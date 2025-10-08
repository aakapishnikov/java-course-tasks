package org.example;

public abstract non-sealed class WaterTransport extends Transport {
    protected WaterTransport(String model, int capacity) {
        super(model, capacity);
    }
    @Override public String category() { return "Водный"; }
}
