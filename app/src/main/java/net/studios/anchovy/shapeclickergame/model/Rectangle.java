package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;
import android.support.annotation.Nullable;

public class Rectangle extends Shape {

    private int width, height;

    public Rectangle(Canvas canvas, int x, int y, int velocity, int width, int height, @Nullable String text) {
        super(canvas, x, y, velocity, text);
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
