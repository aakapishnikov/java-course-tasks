package org.example;

public interface EnginePowered extends Operable, Refuelable {
    Engine getEngine();
    int getFuelLevel();
    int getFuelCapacity();
    void setFuelLevel(int value);

    @Override
    default void start() {
        getEngine().start();
        System.out.println("Двигатель запущен.");
    }
    @Override
    default void stop() {
        getEngine().stop();
        System.out.println("Двигатель остановлен.");
    }
    @Override
    default void refuel(int liters) {
        if (liters <= 0) {
            System.out.println("Укажите положительный объем заправки.");
            return;
        }
        int free = getFuelCapacity() - getFuelLevel();
        int added = Math.min(free, liters);
        setFuelLevel(getFuelLevel() + added);
        System.out.println("Заправлено: " + added + " л. Текущий уровень: " + getFuelLevel() + " л.");
        if (liters > added)
            System.out.println("Бак полон, " + (liters - added) + " л не поместилось.");
    }
    @Override default int fuelLevel() { return getFuelLevel(); }
    @Override default int fuelCapacity() { return getFuelCapacity(); }
}
