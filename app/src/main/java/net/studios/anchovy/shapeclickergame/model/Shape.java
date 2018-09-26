package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;
import android.support.annotation.Nullable;

public abstract class Shape implements Drawable {
    protected int x, y;
    protected double velocity;
    protected String text;
    protected Canvas canvas;
    protected boolean isOnScreen;

    public Shape(Canvas canvas, int x, int y, int velocity, @Nullable String text) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.isOnScreen = false;
        this.text = text;
    }

    public abstract void move();

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

    public void setOnScreen(boolean onScreen) {
        isOnScreen = onScreen;
    }
}
