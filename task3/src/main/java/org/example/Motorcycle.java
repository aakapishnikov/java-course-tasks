package org.example;

public final class Motorcycle extends GroundTransport implements EnginePowered {
    private final Engine engine;
    private final Wheel wheel;
    private final int tank;
    private int fuel;

    public Motorcycle(String model, int capacity) {
        super(model, Math.max(1, capacity), 2);
        engine = new Engine(EngineType.PETROL, FuelType.GASOLINE, 70);
        wheel = new Wheel(17);
        tank = 18;
        fuel = 5;
    }
    @Override public String info() {
        return basicInfo() + ", тип: Мотоцикл, двигатель: [" + engine + "], бак: " + tank + " л, топливо: " + fuel + " л, колёса: " + wheels + " x " + wheel;
    }
    @Override public Engine getEngine() { return engine; }
    @Override public int getFuelLevel() { return fuel; }
    @Override public int getFuelCapacity() { return tank; }
    @Override public void setFuelLevel(int value) { fuel = Math.clamp(0, value, tank); }
}
