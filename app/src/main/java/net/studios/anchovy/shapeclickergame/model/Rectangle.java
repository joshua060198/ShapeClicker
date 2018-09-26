package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.Log;

import net.studios.anchovy.shapeclickergame.GameUtil;

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
        this.canvas.drawRect(x,y,x+width,y+height,this.paintFactory.getPaint(GameUtil.randomInt(0,3)));
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isCollide(Shape other) {
        if (other instanceof Rectangle) {
            return isCollideRectWithRect(this, (Rectangle) other);
        } else if (other instanceof Circle) {
            return isCollideRectWithCircle((Circle)other, this);
        } else return false;
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
