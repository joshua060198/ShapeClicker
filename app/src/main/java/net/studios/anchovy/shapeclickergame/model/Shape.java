package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;

public abstract class Shape implements Drawable {
    protected int x, y, width, height;
    protected double velocity;
    protected String text;
    protected Canvas canvas;
    protected boolean isOnScreen;

    public Shape(Canvas canvas) {
        this.canvas = canvas;
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
        this.velocity = 0;
        this.isOnScreen = false;
    }

    public abstract void move();

    public void outOfScreen() {
        this.isOnScreen = false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isOnScreen() {
        return isOnScreen;
    }
}
