package org.example;

public class Engine {
    private final EngineType type;
    private final FuelType fuel;
    private final int horsepower;
    private boolean running;

    public Engine(EngineType type, FuelType fuel, int horsepower) {
        this.type = type;
        this.fuel = fuel;
        this.horsepower = Math.max(1, horsepower);
    }
    public void start() { running = true; }
    public void stop() { running = false; }
    public boolean isRunning() { return running; }
    public EngineType getType() { return type; }
    public FuelType getFuel() { return fuel; }
    public int getHorsepower() { return horsepower; }

    @Override
    public String toString() {
        return type + ", " + fuel + ", " + horsepower + " л.с.";
    }
}
