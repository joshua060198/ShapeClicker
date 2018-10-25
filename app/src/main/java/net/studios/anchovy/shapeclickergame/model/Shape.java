/*
    Shape Clicker Games v1.0
    Copyright (C) 2018  Anchovy Studios

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;

import net.studios.anchovy.shapeclickergame.GameUtil;
import net.studios.anchovy.shapeclickergame.PaintFactory;

public abstract class Shape implements Drawable {
    protected int x, y;
    protected double velocity;
    protected String text;
    protected Canvas canvas;
    protected PaintFactory paintFactory;
    protected Paint paint;

    public Shape(Canvas canvas, int x, int y, int velocity, @Nullable String text, Paint paint) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.text = text;
        this.paint = paint;
        this.paintFactory = PaintFactory.getInstance();
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

    public Paint getPaint() {
        return paint;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    public abstract void drawSoal(int maxHeight, int maxWidth);

    public abstract boolean isInside(float x, float y);

    public abstract void clear();

    public abstract void clearSoal(int maxWidth, int maxHeight);

    protected boolean isCollideRectWithRect(Rectangle r1, Rectangle r2) {
        if(r1.getX() < r2.getX() + r2.getWidth() &&
                r1.getX() + r1.getWidth() > r2.getX() &&
                r1.getY() < r2.getY() + r2.getHeight() &&
                r1.getY() + r1.getHeight() > r2.getY())
        {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isCollideRectWithCircle(Circle circle, Rectangle rect)
    {
        int dX = circle.getX() - GameUtil.max(rect.getX(), GameUtil.min(circle.getX(), rect.getX()+rect.getWidth()));
        int dY = circle.getY() - GameUtil.max(rect.getY(), GameUtil.min(circle.getY(), rect.getY()+rect.getHeight()));
        if ((dX * dX + dY * dY) < (circle.getRadius() * circle.getRadius())) {
            return true;
        } else {
            return false;
        }
    }

    protected boolean isCollideCircleWithCircle(Circle c1, Circle c2) {
        if ( (((c2.getX()-c1.getX()) * (c2.getX()-c1.getX())) + ((c1.getY()-c2.getY()) * (c1.getY()-c2.getY()))) <=
                ((c1.getRadius()+c2.getRadius()) * (c1.getRadius()+c2.getRadius()))) {
            return true;
        } else {
            return false;
        }
    }
}
