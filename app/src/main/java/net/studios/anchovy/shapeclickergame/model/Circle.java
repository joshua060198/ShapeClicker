package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;
import android.support.annotation.Nullable;

import net.studios.anchovy.shapeclickergame.GameUtil;

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
        this.canvas.drawCircle(x,y,radius,this.paintFactory.getPaint(GameUtil.randomInt(0,3)));
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isCollide(Shape other) {
        if (other instanceof Rectangle) {
            return isCollideRectWithCircle( this, (Rectangle)other);
        } else if (other instanceof Circle) {
            return isCollideCircleWithCircle(this, (Circle) other);
        } else return false;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
