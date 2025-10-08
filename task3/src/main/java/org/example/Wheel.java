package org.example;

public class Wheel {
    private final int diameter;
    public Wheel(int diameter) { this.diameter = Math.max(1, diameter); }
    public int getDiameter() { return diameter; }
    @Override public String toString() { return diameter + "\""; }
}
