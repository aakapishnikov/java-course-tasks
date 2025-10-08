package org.example;

public abstract sealed class Transport permits GroundTransport, AirTransport, WaterTransport {

    protected final String model;
    protected final int capacity;

    protected Transport(String model, int capacity) {
        this.model = model;
        this.capacity = Math.max(0, capacity);
    }

    public abstract String category();
    public abstract String info();

    protected String basicInfo() {
        return "Модель: " + model + ", мест: " + capacity + ", класс: " + category();
    }

    @Override
    public String toString() {
        return info();
    }
}
