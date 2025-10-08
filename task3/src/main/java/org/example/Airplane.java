package org.example;

public final class Airplane extends AirTransport implements EnginePowered {
    private final Engine engine;
    private final int tank;
    private int fuel;

    public Airplane(String model, int capacity) {
        super(model, Math.max(1, capacity));
        engine = new Engine(EngineType.TURBOPROP, FuelType.KEROSENE, 2000);
        tank = 5000;
        fuel = 1200;
    }
    @Override public String info() {
        return basicInfo() + ", тип: Самолет, двигатель: [" + engine + "], бак: " + tank + " л, топливо: " + fuel + " л";
    }
    @Override public Engine getEngine() { return engine; }
    @Override public int getFuelLevel() { return fuel; }
    @Override public int getFuelCapacity() { return tank; }
    @Override public void setFuelLevel(int value) { fuel = Math.max(0, Math.min(tank, value)); }
}
