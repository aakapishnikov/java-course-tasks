package org.example;

public final class Bicycle extends GroundTransport implements Operable {
    private final Wheel wheel;
    private boolean moving;
    public Bicycle(String model) {
        super(model, 1, 2);
        wheel = new Wheel(28);
    }
    @Override public String info() {
        return basicInfo() + ", тип: Велосипед, колёса: " + wheels + " x " + wheel + ", состояние: " + (moving ? "движется" : "стоит");
    }
    @Override public void start() { moving = true; System.out.println("Вы начали крутить педали."); }
    @Override public void stop() { moving = false; System.out.println("Вы остановились."); }
}
