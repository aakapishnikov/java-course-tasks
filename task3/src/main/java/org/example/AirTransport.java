package org.example;

public abstract non-sealed class AirTransport extends Transport {
    protected AirTransport(String model, int capacity) {
        super(model, capacity);
    }
    @Override public String category() { return "Воздушный"; }
}
