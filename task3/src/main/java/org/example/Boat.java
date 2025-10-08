package org.example;

public final class Boat extends WaterTransport implements EnginePowered {
    private final Engine engine;
    private final int tank;
    private int fuel;

    public Boat(String model, int capacity) {
        super(model, Math.max(1, capacity));
        engine = new Engine(EngineType.DIESEL, FuelType.DIESEL, 220);
        tank = 300;
        fuel = 80;
    }
    @Override public String info() {
        return basicInfo() + ", тип: Катер, двигатель: [" + engine + "], бак: " + tank + " л, топливо: " + fuel + " л";
    }
    @Override public Engine getEngine() { return engine; }
    @Override public int getFuelLevel() { return fuel; }
    @Override public int getFuelCapacity() { return tank; }
    @Override public void setFuelLevel(int value) { fuel = Math.max(0, Math.min(tank, value)); }
}
