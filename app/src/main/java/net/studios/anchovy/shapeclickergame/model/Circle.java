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
import android.util.Log;

import net.studios.anchovy.shapeclickergame.GameUtil;
import net.studios.anchovy.shapeclickergame.PaintFactory;

public class Circle extends Shape {
    private int radius;

    public Circle(Canvas canvas, int x, int y, int velocity, int radius, @Nullable String text, Paint paint) {
        super(canvas, x, y, velocity, text, paint);
        this.radius = radius;
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {
        this.canvas.drawCircle(x,y,radius,paint);
    }

    @Override
    public void drawSoal(int maxHeight, int maxWidth) {
        float x = (maxWidth/2.0f);
        float y = (maxHeight+ (GameUtil.JARAK_SOAL/2.0f));
        this.canvas.drawCircle(x,y,radius,paint);
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

    @Override
    public boolean isInside(float x, float y) {
        double d = ((x-(this.x*1.0f))*(x-(this.x*1.0f))) + ((y-(this.y*1.0f))*(y-(this.y*1.0f)));
        return d <= (this.radius*1.0f*this.radius*1.0f);
    }

    @Override
    public void clear() {
        this.canvas.drawCircle(x,y,radius, PaintFactory.getInstance().getPaintByCode(0));
    }

    @Override
    public void clearSoal(int maxWidth, int maxHeight) {
        float x = (maxWidth/2.0f);
        float y = (maxHeight+ (GameUtil.JARAK_SOAL/2.0f));
        this.canvas.drawCircle(x,y,radius,PaintFactory.getInstance().getPaintByCode(0));
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
