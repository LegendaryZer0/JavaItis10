package ru.itis;

public class Ellipse extends Shape {
    private double radiusFirst;
    private double radiusSecond;

    public Ellipse(double x, double y, double radiusFirst, double radiusSecond) {
        super(x, y);
        this.radiusFirst = radiusFirst;
        this.radiusSecond = radiusSecond;
    }

    public double getArea() {
        return Math.PI * radiusFirst * radiusSecond;
    }

    public double getRadiusFirst() {
        return radiusFirst;
    }

    public double getRadiusSecond() {
        return radiusSecond;
    }
}
