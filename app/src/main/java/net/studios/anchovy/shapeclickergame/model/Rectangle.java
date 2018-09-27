package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.Log;

import net.studios.anchovy.shapeclickergame.GameUtil;

public class Rectangle extends Shape {

    private int width, height;

    public Rectangle(Canvas canvas, int x, int y, int velocity, int width, int height, @Nullable String text, Paint paint) {
        super(canvas, x, y, velocity, text, paint);
        this.width = width;
        this.height = height;
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {
        this.canvas.drawRect(x,y,x+width,y+height,paint);
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

    @Override
    public void drawSoal(int maxHeight, int maxWidth) {
        float x = (maxWidth/2.0f) - (this.width/2.0f);
        float y = maxHeight + ((GameUtil.JARAK_SOAL-this.height)/2.0f);
        this.canvas.drawRect(x,y,x+this.width,y+this.height,paint);
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
