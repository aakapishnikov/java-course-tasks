package org.example;

public final class Car extends GroundTransport implements EnginePowered {
    private final Engine engine;
    private final Wheel wheel;
    private final int tank;
    private int fuel;

    public Car(String model, int capacity) {
        super(model, capacity, 4);
        engine = new Engine(EngineType.PETROL, FuelType.GASOLINE, 150);
        wheel = new Wheel(16);
        tank = 55;
        fuel = 10;
    }
    @Override public String info() {
        return basicInfo() + ", тип: Автомобиль, двигатель: [" + engine + "], бак: " + tank + " л, топливо: " + fuel + " л, колёса: " + wheels + " x " + wheel;
    }
    @Override public Engine getEngine() { return engine; }
    @Override public int getFuelLevel() { return fuel; }
    @Override public int getFuelCapacity() { return tank; }
    @Override public void setFuelLevel(int value) { fuel = Math.max(0, Math.min(tank, value)); }
}
