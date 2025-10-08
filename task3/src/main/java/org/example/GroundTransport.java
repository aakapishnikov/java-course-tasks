package org.example;

public abstract non-sealed class GroundTransport extends Transport {
    protected final int wheels;
    protected GroundTransport(String model, int capacity, int wheels) {
        super(model, capacity);
        this.wheels = Math.max(0, wheels);
    }
    @Override public String category() { return "Наземный"; }
}
