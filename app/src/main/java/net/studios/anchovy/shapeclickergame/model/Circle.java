package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;
import android.support.annotation.Nullable;

public class Circle extends Shape {
    private int radius;

    public Circle(Canvas canvas, int x, int y, int velocity, int radius, @Nullable String text) {
        super(canvas, x, y, velocity, text);
        this.radius = radius;
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {

    }

    @Override
    public void update() {

    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
